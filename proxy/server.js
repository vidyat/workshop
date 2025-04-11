const express = require('express');
const { createProxyMiddleware } = require('http-proxy-middleware');
const logger = require('./logger');
const app = express();

// Add request logging middleware
app.use((req, res, next) => {
  const start = Date.now();
  
  // Log request
  logger.logRequest(req);
  
  // Override end method to log response
  const originalEnd = res.end;
  res.end = function(chunk, encoding) {
    const responseTime = Date.now() - start;
    logger.logResponse(res, responseTime);
    return originalEnd.call(this, chunk, encoding);
  };
  
  next();
});

// Configure proxy middleware
app.use('/proxy/8090', createProxyMiddleware({
    target: 'http://localhost:8090',
    changeOrigin: true,
    secure: false,
    protocolRewrite: 'http',
    pathRewrite: {
        '^/proxy/8090': '/'
    },
    logLevel: 'silent', // We'll handle logging ourselves
    onProxyReq: (proxyReq, req, res) => {
        const requestBody = req.body ? logger.safeStringify(req.body) : '';
        logger.debug('Proxying request', {
            method: req.method,
            url: req.url,
            targetUrl: `${proxyReq.protocol}//${proxyReq.host}${proxyReq.path}`,
            headers: proxyReq.getHeaders(),
            body: requestBody.length > 0 ? requestBody : '(empty or not captured)'
        });
    },
    onProxyRes: (proxyRes, req, res) => {
        logger.debug('Received proxy response', {
            statusCode: proxyRes.statusCode,
            statusMessage: proxyRes.statusMessage,
            url: req.url,
            headers: proxyRes.headers,
            size: proxyRes.headers['content-length'] || 'unknown'
        });
        
        // For specific content types, we could capture and log the body
        // This requires additional stream handling which is omitted for brevity
    },
    onError: (err, req, res) => {
        logger.error('Proxy error', {
            error: err.message,
            stack: err.stack,
            method: req.method,
            url: req.url
        });
        
        // Send an error response to the client
        if (!res.headersSent) {
            res.status(500).json({
                message: 'Proxy error',
                error: err.message
            });
        }
    }
}));

// Error handling middleware
app.use((err, req, res, next) => {
    logger.error('Application error', {
        error: err.message,
        stack: err.stack,
        method: req.method,
        url: req.url
    });
    
    res.status(500).json({
        message: 'Internal server error',
        error: err.message
    });
});

// Start the server
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    logger.info(`Proxy server started`, {
        port: PORT,
        environment: process.env.NODE_ENV || 'development',
        nodeVersion: process.version
    });
});
