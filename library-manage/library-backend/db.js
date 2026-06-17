const mysql = require('mysql2');

// 这里改成你自己的 MySQL 账号密码
const db = mysql.createConnection({
  host: 'localhost',
  user: 'root',         // 你的MySQL用户名
  password: 'Jianye@100', // 改成你的密码
  database: 'library1'
});

db.connect((err) => {
  if (err) {
    console.error('❌ 数据库连接失败：', err.message);
    return;
  }
  console.log('✅ 数据库连接成功！');
});

module.exports = db;