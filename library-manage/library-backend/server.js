const express = require('express');
const cors = require('cors');
const db = require('./db');
const app = express();

// 中间件
app.use(cors()); // 允许跨域（前端访问后端）
app.use(express.json()); // 解析JSON请求体

// ---------------- 1. 登录接口 ----------------
app.post('/api/login', (req, res) => {
  const { username, password } = req.body;
  const sql = 'SELECT * FROM admin WHERE username = ? AND password = ?';
  db.query(sql, [username, password], (err, result) => {
    if (err) return res.json({ code: 500, msg: '服务器错误' });
    if (result.length === 0) {
      return res.json({ code: 400, msg: '账号或密码错误' });
    }
    res.json({ code: 200, msg: '登录成功', user: result[0] });
  });
});

// ---------------- 2. 图书检索接口 ----------------
app.get('/api/book/search', (req, res) => {
  const keyword = req.query.keyword || '';
  const sql = `
    SELECT b.*, bc.barcode, bc.location, bc.status 
    FROM book b
    LEFT JOIN bookcopy bc ON b.isbn = bc.book_isbn
    WHERE b.name LIKE ? OR b.isbn LIKE ? OR b.author LIKE ?
  `;
  db.query(sql, [`%${keyword}%`, `%${keyword}%`, `%${keyword}%`], (err, data) => {
    if (err) return res.json({ code: 500, msg: '查询失败' });
    res.json({ code: 200, data });
  });
});

// ---------------- 3. 借书接口 ----------------
app.post('/api/borrow', (req, res) => {
  const { cardNo, bookBarcode } = req.body;
  const today = new Date();
  const dueDate = new Date(today);
  dueDate.setDate(dueDate.getDate() + 30); // 借期30天

  const sql = `
    INSERT INTO borrowrec (card_no, book_barcode, borrow_date, due_date, status)
    VALUES (?, ?, ?, ?, '借阅中')
  `;
  db.query(sql, [cardNo, bookBarcode, today, dueDate], (err) => {
    if (err) return res.json({ code: 500, msg: '借书失败' });
    // 更新图书副本状态为已借
    db.query('UPDATE bookcopy SET status = ? WHERE barcode = ?', ['已借', bookBarcode]);
    res.json({ code: 200, msg: '借书成功' });
  });
});

// ---------------- 4. 还书接口 ----------------
app.post('/api/return', (req, res) => {
  const { bookBarcode } = req.body;
  const today = new Date();

  const sql = `
    UPDATE borrowrec 
    SET return_date = ?, status = '已还'
    WHERE book_barcode = ? AND status = '借阅中'
  `;
  db.query(sql, [today, bookBarcode], (err) => {
    if (err) return res.json({ code: 500, msg: '还书失败' });
    // 更新图书副本状态为可借
    db.query('UPDATE bookcopy SET status = ? WHERE barcode = ?', ['可借', bookBarcode]);
    res.json({ code: 200, msg: '还书成功' });
  });
});

// 启动服务
const PORT = 3000;
app.listen(PORT, () => {
  console.log(`✅ 后端服务已启动：http://localhost:${PORT}`);
});