# fake-shopping-cart-server

## 启动
`npm install & npm start`

## API
```
# /products
GET http://localhost:3001/products
GET http://localhost:3001/products/1
POST http://localhost:3001/products
PUT http://localhost:3001/products/1
PATCH http://localhost:3001/products/1
DELETE http://localhost:3001/products/1
  {
      "id": 1,
      "name": "法拉利",
      "description": "法拉利跑车作为世界上唯一一家始终将 F1 技术应用到新车上的公司，法拉利制造了现今最好的高性能公路跑车，因而备感自豪。",
      "price": 100
  }

# /cart-products
GET http://localhost:3001/cart-products
GET http://localhost:3001/cart-products/1
POST http://localhost:3001/cart-products
PUT http://localhost:3001/cart-products/1
PATCH http://localhost:3001/cart-products/1
DELETE http://localhost:3001/cart-products/1
  {
      "productId": 4,
      "quantitiy": 5,
      "id": 1
  }

# /profile
GET http://localhost:3001/profile
POST http://localhost:3001/profile
  {
      "name": "法外狂徒张三",
      "phone": "123456789"
  }

```

## example
![image](https://github.com/demongodYY/fake-shopping-cart-server/assets/17036920/171abd12-67c9-4b3c-bd9b-a5f7ea2eaf02)
![image](https://github.com/demongodYY/fake-shopping-cart-server/assets/17036920/3f0cd841-6b52-455c-818c-943fd0c4cfb8)
![image](https://github.com/demongodYY/fake-shopping-cart-server/assets/17036920/57e02df6-b7bb-41ae-af40-44dc5cb1a183)
![image](https://github.com/demongodYY/fake-shopping-cart-server/assets/17036920/15f8970b-5725-40fb-8180-d2d7fa0652be)
![image](https://github.com/demongodYY/fake-shopping-cart-server/assets/17036920/271b3816-5238-4f48-9198-4124479dfc8d)
![image](https://github.com/demongodYY/fake-shopping-cart-server/assets/17036920/2ca12f77-1885-472d-8628-ab2eb86b7bb2)





