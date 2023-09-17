import '../../styles/shop/cart.scss';
import React, { useEffect, useRef, useState } from 'react';
import CartItem, { CartItemProps } from '../../components/CartItem.tsx';
import { useRecoilState, useRecoilValue } from 'recoil';
import { allProducts, cartDetail, showGlobalLoading } from '../../store/store.ts';
import { CartProduct } from '../../models/shop/CartProduct.ts';
import { Button, Checkbox, Drawer, Empty, message, Radio, RadioChangeEvent, Space, Tag } from 'antd';
import { useNavigate } from 'react-router-dom';
import { ALL_PROMOTION, Promotion, PromotionType } from '../../models/shop/PromotionList.ts';
import shopAPI from '../../api/shop/ShopAPI.ts';

const Cart: React.FC = () => {
  const navigate = useNavigate();
  const [drawerOpen, setDrawerOpen] = useState<boolean>(false);
  const [promotionType, setPromotionType] = useState<PromotionType>();
  const [_, setGlobalLoadingStatus] = useRecoilState(showGlobalLoading);
  const [cart, setCartDetail] = useRecoilState(cartDetail);
  const cartRef = useRef<CartProduct[]>();
  cartRef.current = cart;
  const products = useRecoilValue(allProducts);

  const onSelectedChanged = (cartId: number) => {
    setCartProductItems((cartProducts: CartItemProps[]) =>
      cartProducts.map((item: CartItemProps) => {
        if (item.cartId === cartId) {
          return {
            ...item,
            selected: !item.selected,
          };
        }
        return item;
      }),
    );
  };

  // operator cart code
  const operateCartProductQuantity = (productId: number, step: number) => {
    setGlobalLoadingStatus(true);
    let needUpdateItem = {} as CartProduct;
    const currentItem = JSON.parse(JSON.stringify(cartRef.current))
      ?.map((item: CartProduct) => {
        if (item.productId === productId) {
          item.quantity += step;
          needUpdateItem = item;
        }
        return item;
      })
      .filter((item: CartProduct) => item.quantity > 0);
    if (needUpdateItem.quantity > 0) {
      shopAPI
        .updateCartProductQuantity(needUpdateItem)
        .then((_: CartProduct) => {
          message.success('商品数量更新成功~').then((_) => {});
        })
        .finally(() => setGlobalLoadingStatus(false));
    } else {
      shopAPI
        .deleteCartProductItem(needUpdateItem.id)
        .then((_: void) => {
          message.success('删除商品成功~').then((_) => {});
        })
        .finally(() => setGlobalLoadingStatus(false));
    }
    setCartDetail(currentItem);
  };

  const transferCartToCartProductItems = () =>
    cart.map((cartItem: CartProduct) => {
      const currentProduct = products.find((product) => product.id === cartItem.productId) ?? { price: 0, name: '' };
      const productTotalPrice = currentProduct.price * cartItem.quantity ?? 0;
      return {
        cartId: cartItem.id,
        productId: cartItem.productId,
        name: currentProduct?.name,
        quantity: cartItem.quantity,
        banner: 'https://source.unsplash.com/random/products?random=' + Math.random(),
        price: productTotalPrice,
        discountPrice: productTotalPrice,
        selected: true,
        onSelectedChanged,
        operateCartProductQuantity,
      };
    });
  const [cartProductItems, setCartProductItems] = useState<CartItemProps[]>(transferCartToCartProductItems());

  useEffect(() => {
    setCartProductItems((_: CartItemProps[]) => transferCartToCartProductItems());
  }, [JSON.stringify(cart.map((item) => ({ productId: item.productId, quantity: item.quantity })))]);

  useEffect(() => {
    setGlobalLoadingStatus(true);
    calculatePromotion();
    setInterval(() => setGlobalLoadingStatus(false), 500);
  }, []);

  useEffect(() => {
    calculatePromotion(promotionType);
  }, [
    promotionType,
    JSON.stringify(cartProductItems.map((item) => ({ selected: item.selected, quantity: item.quantity }))),
  ]);

  // calculate promotion code
  const calculatePromotion = (type?: PromotionType) => {
    const totalPrice = cartProductItems
      .filter((item) => item.selected)
      .reduce((accumulator: number, currentCartProduct) => {
        return accumulator + currentCartProduct.price;
      }, 0);
    let maximumPromotion = 0;
    switch (type) {
      case PromotionType.NONE_PROMOTION:
        maximumPromotion = 0;
        break;
      case PromotionType.NINE_DISCOUNT:
        maximumPromotion = totalPrice * 0.1;
        break;
      case PromotionType.FULL_1000_OFF_150:
        maximumPromotion = Math.floor(totalPrice / 1000) * 150;
        break;
      case PromotionType.FULL_3000_OFF_500:
        maximumPromotion = Math.floor(totalPrice / 3000) * 500;
        break;
      default:
        const full100Off150Price = Math.floor(totalPrice / 1000) * 150;
        const nineDiscountPrice = totalPrice * 0.1;
        const full3000Off500Price = Math.floor(totalPrice / 3000) * 500;
        maximumPromotion = Math.max(full100Off150Price, nineDiscountPrice, full3000Off500Price);
        setPromotionType((_: PromotionType | undefined) => {
          if (maximumPromotion === full100Off150Price) {
            return PromotionType.FULL_1000_OFF_150;
          } else if (maximumPromotion === full3000Off500Price) {
            return PromotionType.FULL_3000_OFF_500;
          } else if (maximumPromotion === nineDiscountPrice) {
            return PromotionType.NINE_DISCOUNT;
          } else {
            return PromotionType.NONE_PROMOTION;
          }
        });
        break;
    }
    if (type) {
      setPromotionType((_: PromotionType | undefined) => type);
    }
    setCartProductItems((cartProducts: CartItemProps[]) => {
      setInterval(() => setGlobalLoadingStatus(false), 500);
      return cartProducts.map((cartProduct) => ({
        ...cartProduct,
        discountPrice: cartProduct.selected
          ? parseFloat((cartProduct.price - (cartProduct.price / totalPrice) * maximumPromotion).toFixed(1))
          : cartProduct.price,
      }));
    });
  };

  // selected state code
  const clickSelectAll = () => {
    setCartProductItems((prev: CartItemProps[]) =>
      prev.map((item) => ({
        ...item,
        selected: !cartProductItems.every((item) => item.selected),
      })),
    );
  };

  return (
    <div className="cart-container">
      <div className="cart-header">
        <p className="cart-title">购物车</p>
      </div>

      {cartProductItems.length > 0 ? (
        <div className="cart-content">
          <div className="promotion-bar">
            <div>
              {ALL_PROMOTION.filter((promotion) => promotion.promotionType === promotionType).map((promotion) => (
                <div key={promotion.promotionType}>
                  <Tag color="#cd201f">{promotion.promotionName}</Tag>
                  <span className="promotion-desc">{promotion.promotionDesc}</span>
                </div>
              ))}
            </div>
            <span className="select-promotion" onClick={() => setDrawerOpen((_: boolean) => true)}>
              选择优惠 {'>'}
            </span>
          </div>
          {cartProductItems.map((cartItem: CartItemProps) => (
            <CartItem key={cartItem.cartId} {...cartItem} />
          ))}
        </div>
      ) : (
        <Empty
          className="empty-cart"
          image="https://gw.alipayobjects.com/zos/antfincdn/ZHrcdLPrvN/empty.svg"
          imageStyle={{ height: 60 }}
          description={<span>您还没有添加商品哦～</span>}
        >
          <Button type="primary" shape="round" onClick={() => navigate('/shop')}>
            去逛逛
          </Button>
        </Empty>
      )}

      <div className="cart-footer">
        <Checkbox
          indeterminate={
            cartProductItems.some((item) => !item.selected) && cartProductItems.some((item) => item.selected)
          }
          checked={cartProductItems.every((item) => item.selected)}
          style={{ color: '#7f7f7f', marginLeft: '10px' }}
          onClick={clickSelectAll}
        >
          全选
        </Checkbox>

        <div>
          <span>
            <span className="total-text">总计: &nbsp;</span>
            {cartProductItems.filter((item) => item.selected).reduce((prev, cur) => prev + cur.discountPrice, 0) !==
            cartProductItems.filter((item) => item.selected).reduce((prev, cur) => prev + cur.price, 0) ? (
              <>
                <span className="discount-price">
                  ¥{cartProductItems.filter((item) => item.selected).reduce((prev, cur) => prev + cur.discountPrice, 0)}
                </span>
                <span className="original-price">
                  ¥{cartProductItems.filter((item) => item.selected).reduce((prev, cur) => prev + cur.price, 0)}
                </span>
              </>
            ) : (
              <span className="discount-price">
                ¥{cartProductItems.filter((item) => item.selected).reduce((prev, cur) => prev + cur.discountPrice, 0)}
              </span>
            )}
          </span>
          <Button
            type="primary"
            shape="round"
            disabled={
              cartProductItems.filter((item) => item.selected).reduce((prev, current) => prev + current.quantity, 0) < 1
            }
            style={{ marginLeft: '10px', marginRight: '10px' }}
          >
            去结算(
            {cartProductItems.filter((item) => item.selected).reduce((prev, current) => prev + current.quantity, 0)})
          </Button>
        </div>
      </div>

      <Drawer
        closable={false}
        height={220}
        open={drawerOpen}
        onClose={() => setDrawerOpen((_: boolean) => false)}
        placement={'bottom'}
        bodyStyle={{ padding: '0 !important' }}
      >
        <div className="promotion-top-bar">
          <span>选择优惠</span>
          <span className="close-drawer-btn" onClick={() => setDrawerOpen((_: boolean) => false)}>
            X
          </span>
        </div>
        <Radio.Group
          value={promotionType}
          onChange={(e: RadioChangeEvent) => {
            setPromotionType((_: PromotionType | undefined) => e.target.value);
            calculatePromotion(e.target.value);
            setDrawerOpen((_: boolean) => false);
          }}
        >
          <Space direction="vertical">
            {ALL_PROMOTION.map((promotion: Promotion) => (
              <div className="promotion-item" key={promotion.promotionType}>
                <Radio value={promotion.promotionType}>
                  <Tag color="#cd201f">{promotion.promotionName}</Tag>
                  {promotion.promotionDesc}
                </Radio>
              </div>
            ))}
          </Space>
        </Radio.Group>
      </Drawer>
    </div>
  );
};

export default Cart;
