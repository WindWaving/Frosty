const {sequelize,Sequelize}=require("./db")

const Picture=sequelize.define('picture',{
    pid:{
        type:Sequelize.INTEGER,
        autoIncrement:true,
        primaryKey:true
    },
    imgUrl:Sequelize.STRING,
    //authorId:Sequelize.INTEGER,
    likes:{
        type:Sequelize.INTEGER,
        defaultValue:0
    },
    content:Sequelize.TEXT
})

module.exports=Picture