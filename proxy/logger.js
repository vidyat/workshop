const winston = require('winston');
const path = require('path');
const fs = require('fs');

// Create logs directory if it doesn't exist
const logDir = path.join(__dirname, 'logs');
if (!fs.existsSync(logDir)) {
  fs.mkdirSync(logDir);
}

// Define custom format with timestamp, log level, and message
const customFormat = winston.format.combine(
  winston.format.timestamp({ format: 'YYYY-MM-DD HH:mm:ss.SSS' }),
  winston.format.printf(({ timestamp, level, message, ...meta }) => {
    return `${timestamp} [${level.toUpperCase()}]: ${message} ${
      Object.keys(meta).length ? JSON.stringify(meta, null, 2) : ''
    }`;
  })
);

// Create logger instance
const logger = winston.createLogger({
  level: process.env.LOG_LEVEL || 'info',
  format: customFormat,
  transports: [
    // Console transport
    new winston.transports.Console({
      format: winston.format.combine(
        winston.format.colorize(),
        customFormat
      )
    }),
    // File transport for all logs
    new winston.transports.File({ 
      filename: path.join(logDir, 'proxy.log'),
      maxsize: 5242880, // 5MB
      maxFiles: 5,
    }),
    // Separate file for error logs
    new winston.transports.File({ 
      filename: path.join(logDir, 'error.log'), 
      level: 'error',
      maxsize: 5242880, // 5MB
      maxFiles: 5,
    })
  ]
});

// Helper function to safely stringify objects (handles circular references)
logger.safeStringify = (obj) => {
  if (!obj) return '';
  const cache = new Set();
  return JSON.stringify(obj, (key, value) => {
    if (typeof value === 'object' && value !== null) {
      if (cache.has(value)) {
        return '[Circular Reference]';
      }
      cache.add(value);
    }
    return value;
  }, 2);
};

// Helper to log request details
logger.logRequest = (req) => {
  const requestInfo = {
    method: req.method,
    url: req.url,
    headers: req.headers,
    params: req.params,
    query: req.query,
  };
  
  // Only log body for non-GET requests and if it exists
  if (req.method !== 'GET' && req.body) {
    // Avoid logging sensitive information
    const sanitizedBody = { ...req.body };
    // Remove potential sensitive fields
    ['password', 'token', 'apiKey', 'secret'].forEach(field => {
      if (sanitizedBody[field]) sanitizedBody[field] = '***REDACTED***';
    });
    requestInfo.body = sanitizedBody;
  }
  
  logger.info(`Incoming request: ${req.method} ${req.url}`, requestInfo);
};

// Helper to log response details
logger.logResponse = (res, responseTime) => {
  logger.info(`Response sent: ${res.statusCode}`, {
    statusCode: res.statusCode,
    responseTime: `${responseTime}ms`,
    headers: res.getHeaders()
  });
};

module.exports = logger;
