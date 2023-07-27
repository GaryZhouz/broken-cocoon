import { atom } from 'recoil';
import { CartProduct } from '../models/shop/CartProduct.ts';
import shopAPI from '../api/shop/ShopAPI.ts';

export const showGlobalLoading = atom<boolean>({
  key: 'showGlobalLoading',
  default: false,
});

export const cartDetail = atom<CartProduct[]>({
  key: 'cartDetail',
  default: await shopAPI.getCart(),
});
