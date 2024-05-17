const express = require('express');
const cors = require('cors');
const appRouter = require('./routers/app-router');
const app = express();

app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.use('/', appRouter);

module.exports = app