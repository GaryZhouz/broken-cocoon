import '../styles/shop/cart-item.scss';
import React, { memo } from 'react';
import { Button, Checkbox, Image, Skeleton } from 'antd';

export interface CartItemProps {
  cartId: number;
  productId: number;
  banner: string;
  name: string;
  price: number;
  discountPrice: number;
  quantity: number;
  selected: boolean;
  onSelectedChanged: (cartId: number) => void;
  operateCartProductQuantity: (productId: number, step: number) => void;
}

const CartItem: React.FC<CartItemProps> = memo((props: CartItemProps) => {
  return (
    <div className="cart-item-container">
      <div className="checkbox-with-banner">
        <Checkbox checked={props.selected} onClick={() => props.onSelectedChanged(props.cartId)}></Checkbox>
        <Image
          className="cart-item-banner"
          src={props.banner}
          preview={false}
          alt="商品图片"
          placeholder={
            <Skeleton.Image style={{ width: 'calc(100% - 10px) ', height: '100%', marginLeft: '10px' }} active />
          }
        />
      </div>
      <div className="cart-item-desc">
        <p className="cart-item-title">{props.name}</p>
        <div className="price-with-count">
          <span>
            {props.discountPrice !== props.price ? (
              <>
                <span className="discount-price">¥{props.discountPrice}</span>
                <span className="original-price">¥{props.price}</span>
              </>
            ) : (
              <span className="discount-price">¥{props.price}</span>
            )}
          </span>
          <div className="counter">
            <Button shape="circle" size="small" onClick={() => props.operateCartProductQuantity(props.productId, -1)}>
              -
            </Button>
            <span className="count-number">{props.quantity ?? 1}</span>
            <Button
              type="primary"
              shape="circle"
              size="small"
              onClick={() => props.operateCartProductQuantity(props.productId, 1)}
            >
              +
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
});

export default CartItem;
