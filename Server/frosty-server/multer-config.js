const multer = require('@koa/multer') // 引入

const storage = multer.diskStorage({
    destination: function (req, file, cb) {
        cb(null, 'public/images')
    },
    filename: function (req, file, cb) {
        let type = file.originalname.split('.')[1]
        cb(null, `${file.fieldname}-${Date.now().toString(16)}.${type}`)
    }
})

const limits = {
    files: 9//文件数量
}
const upload = multer({storage,limits})

module.exports=upload