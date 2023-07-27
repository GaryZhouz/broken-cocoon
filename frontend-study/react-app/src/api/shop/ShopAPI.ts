import { Product } from '../../models/shop/Product.ts';
import axios, { AxiosResponse } from 'axios';
import { message } from 'antd';
import { CartProduct } from '../../models/shop/CartProduct.ts';
import { AddCartRequest } from '../../models/shop/AddCartRequest.ts';

const { VITE_SEVER_HOST, VITE_SEVER_TIMEOUT } = import.meta.env;

const service = axios.create({
  baseURL: VITE_SEVER_HOST,
  timeout: VITE_SEVER_TIMEOUT,
});

service.interceptors.response.use(
  (response: AxiosResponse<any>) => response.data,
  (error: any) => {
    message.error('请求接口出错').then((_) => {});
    return Promise.reject(error);
  },
);

export default {
  getProducts(): Promise<(Product & { banner: string })[]> {
    return service({
      url: '/products',
      method: 'GET',
    });
  },
  getCart(cartId?: number): Promise<CartProduct[]> {
    return service({
      url: 'cart-products/' + (cartId ?? ''),
      method: 'GET',
    });
  },
  addCart(body: AddCartRequest): Promise<CartProduct> {
    return service({
      url: 'cart-products',
      method: 'POST',
      data: body,
    });
  },
  updateCart(body: CartProduct): Promise<CartProduct> {
    return service({
      url: 'cart-products/' + body.id,
      method: 'PATCH',
      data: body,
    });
  },
};
