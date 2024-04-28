const express = require('express')
const app = express()
const port = 3000

app.get('/api/orders', (req, res) => {
  res.json({"msg": "Hello from Express!"})
})

app.post('/api/orders', (req, res) => {
    let contentType = req.headers['content-type']
    console.log(contentType)
    if (contentType !== 'application/json') 
        return res.sendStatus(415)     // unsupported media type
    

    res.json({"msg": "empty response ^_^"})
})

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})