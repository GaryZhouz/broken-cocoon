import { atom } from 'recoil';
import { CartProduct } from '../models/shop/CartProduct.ts';
import shopAPI from '../api/shop/ShopAPI.ts';
import { Product } from '../models/shop/Product.ts';

export const showGlobalLoading = atom<boolean>({
  key: 'showGlobalLoading',
  default: false,
});

export const cartDetail = atom<CartProduct[]>({
  key: 'cartDetail',
  default: await shopAPI.getCart(),
});

export const allProducts = atom<Product[]>({
  key: 'allProducts',
  default: await shopAPI.getProducts().then((resp: Product[]) =>
    resp.map((item: Product) => ({
      ...item,
      banner: 'https://source.unsplash.com/random/products?random=' + Math.random(),
    })),
  ),
});
