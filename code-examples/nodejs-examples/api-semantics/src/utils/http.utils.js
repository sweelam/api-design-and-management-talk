function isApplicationJson(req) {
    const contentType = req.headers['content-type'];
    return contentType === 'application/json'
}

function allFieldsAvailable(obj, src) {
    if (!obj) return false
    return Object.keys(obj).length == Object.keys(src).length
}

module.exports = {
    isApplicationJson,
    allFieldsAvailable
} 