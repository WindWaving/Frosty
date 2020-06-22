const { User,Sequelize } = require('../../models')
const crypto = require('crypto')
var router = require('koa-router')();
router.prefix('/user')
const Op=Sequelize.Op

//注册用户
router.post('/register', async (ctx) => {
    let { nickname, password, phoneNumber } = ctx.request.body;
    let res = await User.findOne({
        where: { nickname: nickname }
    })
    if (res) {
        ctx.body = {
            err: -1,
            info: "该昵称已经使用"
        }
    } else {
        res = await User.findOne({
            where: { phoneNumber: phoneNumber }
        })
        if (res) {
            ctx.body = {
                err: -1,
                info: "该手机号已经注册"
            }
        } else {
            try {
                let md5 = crypto.createHash("md5");
                let hashPswd = md5.update(password).digest("hex");
                await User.create({
                    nickname:nickname,
                    password:hashPswd,
                    phoneNumber:phoneNumber
                })
                ctx.body={
                    err: 0,
                    info: "成功注册"
                }
            }catch(err){
                ctx.body={
                    err:-2,
                    info:"注册失败，"+err
                }
            }
        }
    }
})

//用户登录
router.post('/login',async(ctx)=>{
    let {userfield,password}=ctx.request.body;
    let res=await User.findOne({
        where:{
            [Op.or]:[
                {nickname:userfield},
                {phoneNumber:userfield}
            ]
        }
    })
    if(!res){
        ctx.body={
            err:-1,
            info:"账号未注册"
        }
    }else{
        let md5 = crypto.createHash("md5");
        let hashPswd = md5.update(password).digest("hex");
        if(res.password===hashPswd){
            ctx.body={
                err:0,
                info:"登录成功"
            }
        }else{
            ctx.body={
                err:-1,
                info:"密码错误"
            }
        }
    }
})

//修改用户信息
router.patch('/',async(ctx)=>{
    let {nickname,phoneNumber,oldPass,newPass,avatarUrl}=ctx.request.body;
    try{
        let res=await User.findOne({
            where:{phoneNumber:phoneNumber}
        })
        let m1=crypto.createHash('md5')
        let oldHash=m1.update(oldPass).digest('hex')
        let m2=crypto.createHash('md5')
        let newHash=m2.update(newPass).digest('hex')
        if(res&&res.password==oldHash){
            await User.update({
                nickname:nickname,
                password:newHash,
                avatarUrl:avatarUrl
            },{
                where:{
                    phoneNumber:phoneNumber
                }
            })
            ctx.body={
                err:0,
                info:"修改成功"
            }
        }else{
            ctx.body={
                err:-1,
                info:"原始密码错误"
            }
        }
        
    }catch(err){
        ctx.body={
            err:-2,
            info:"修改错误，"+err
        }
    }
})

module.exports = router