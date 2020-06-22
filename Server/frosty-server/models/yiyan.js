const {sequelize,Sequelize}=require("./db")

const Yiyan=sequelize.define('yiyan',{
    yid:{
        type:Sequelize.INTEGER,
        autoIncrement:true,
        primaryKey:true
    },
    // authorId:{
    //     type:Sequelize.INTEGER
    // },
    content:{
        type:Sequelize.TEXT
    },
    likes:{
        type:Sequelize.INTEGER,
        defaultValue:0
    }
})

module.exports=Yiyan