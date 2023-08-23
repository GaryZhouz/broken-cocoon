import '../../styles/shop/shop.scss';
import React, { useEffect, useRef } from 'react';
import ProductList from '../../components/ProductList.tsx';
import shopAPI from '../../api/shop/ShopAPI.ts';
import { Product } from '../../models/shop/Product.ts';
import { useRecoilState } from 'recoil';
import { allProducts, cartDetail, showGlobalLoading } from '../../store/store.ts';
import { CartProduct } from '../../models/shop/CartProduct.ts';
import { message } from 'antd';

const Shop: React.FC = () => {
  const [products, setProducts] = useRecoilState<Product[]>(allProducts);
  const [_, setGlobalLoadingStatus] = useRecoilState(showGlobalLoading);
  const [cart, setCartDetail] = useRecoilState(cartDetail);
  const cartRef = useRef<CartProduct[]>();
  cartRef.current = cart;
  const addCart = (productId: number) => {
    setGlobalLoadingStatus(true);
    if (cartRef.current?.every((item: CartProduct) => item.productId !== productId)) {
      shopAPI
        .addCart({
          productId,
          quantity: 1,
        })
        .then((resp: CartProduct) => {
          message.success('添加购物车成功').then((_) => {});
          setCartDetail([...(cartRef.current as []), resp]);
        })
        .finally(() => setGlobalLoadingStatus(false));
    } else {
      let needUpdateItem = {} as CartProduct;
      const currentItem = JSON.parse(JSON.stringify(cartRef.current))?.map((item: CartProduct) => {
        if (item.productId === productId) {
          item.quantity += 1;
          needUpdateItem = item;
        }
        return item;
      });
      shopAPI
        .updateCartProductQuantity(needUpdateItem)
        .then((_: CartProduct) => {
          message.success('添加购物车成功').then((_) => {});
        })
        .finally(() => setGlobalLoadingStatus(false));
      setCartDetail(currentItem);
    }
  };
  useEffect(() => {
    setGlobalLoadingStatus(true);
    shopAPI
      .getProducts()
      .then((resp: Product[]) =>
        setProducts(
          resp.map((item: Product) => ({
            ...item,
            banner: 'https://source.unsplash.com/random/products?random=' + Math.random(),
            addCartCallback: addCart,
          })),
        ),
      )
      .finally(() => setGlobalLoadingStatus(false));
  }, []);

  return (
    <div className="product-list-container">
      <div className="product-list-header">
        <p className="product-list-title">商城首页</p>
      </div>

      <div className="product-list">
        {products.length > 0 && products.every((item: any) => item.addCartCallback) && (
          <ProductList products={products} />
        )}
      </div>
    </div>
  );
};
export default Shop;
