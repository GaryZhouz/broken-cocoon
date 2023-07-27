import '../../styles/shop/shop.scss';
import React, { useEffect, useState } from 'react';
import ProductList from '../../components/ProductList.tsx';
import shopAPI from '../../api/shop/ShopAPI.ts';
import { Product } from '../../models/shop/Product.ts';
import { useRecoilState } from 'recoil';
import { cartDetail, showGlobalLoading } from '../../store/store.ts';
import { CartProduct } from '../../models/shop/CartProduct.ts';
import { message } from 'antd';

const Shop: React.FC = () => {
  const [products, setProducts] = useState<Product[]>();
  const [_, setGlobalLoadingStatus] = useRecoilState(showGlobalLoading);
  const [cart, setCartDetail] = useRecoilState(cartDetail);
  const addCart = (productId: number) => {
    setGlobalLoadingStatus(true);
    if (cart.every((item: CartProduct) => item.productId !== productId)) {
      shopAPI
        .addCart({
          productId,
          quantity: 1,
        })
        .then((resp: CartProduct) => {
          message.success('添加购物车成功').then((_) => {});
          setCartDetail([...cart, resp]);
        })
        .finally(() => setGlobalLoadingStatus(false));
    } else {
      let needUpdateItem = {} as CartProduct;
      const currentItem = JSON.parse(JSON.stringify(cart))?.map((item: CartProduct) => {
        if (item.productId === productId) {
          item.quantity += 1;
        }
        needUpdateItem = item;
        return item;
      });
      shopAPI
        .updateCart(needUpdateItem)
        .then((_: CartProduct) => message.success('添加购物车成功'))
        .finally(() => setGlobalLoadingStatus(false));
      setCartDetail(currentItem);
    }
  };
  useEffect(() => {
    setGlobalLoadingStatus(true);
    shopAPI
      .getProducts()
      .then((resp: Product[]) =>
        setProducts((prev: Product[] = []) => {
          prev.splice(0, prev.length);
          prev.push(
            ...resp.map((item: Product) => ({
              ...item,
              banner: 'https://source.unsplash.com/random/products?random=' + Math.random(),
              addCartCallback: addCart,
            })),
          );
          return prev;
        }),
      )
      .finally(() => setGlobalLoadingStatus(false));
  }, []);

  return (
    <div className="product-list-container">
      <div className="product-list-header">
        <p className="product-list-title">商城首页</p>
      </div>

      <div className="product-list">{products !== undefined && <ProductList products={products} />}</div>
    </div>
  );
};
export default Shop;
