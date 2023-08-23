import '../styles/shop/product-item.scss';
import React, { memo } from 'react';
import { Button, Image, Skeleton } from 'antd';
import { PlusOutlined } from '@ant-design/icons';

export interface ProductItemProps {
  id: number;
  banner: string;
  name: string;
  description: string;
  price: number;
  addCartCallback?: (productId: number) => void;
}

const ProductItem: React.FC<ProductItemProps> = memo((props: ProductItemProps) => {
  const addCartCallback = props.addCartCallback ?? (() => {});
  return (
    <div className="product-item-box">
      <div className="product-banner">
        <Image
          className="banner"
          src={props.banner}
          preview={false}
          alt="商品图片"
          placeholder={<Skeleton.Image style={{ width: '100%', height: '100%' }} active />}
        />
      </div>

      <div className="product-desc">
        <h4 className="title">{props.name}</h4>
        <h4 className="description">{props.description}</h4>
        <div className="price-with-add-btn">
          <span className="price">
            <span className="price-unit">¥</span>
            <span className="price-value">{props.price}</span>
          </span>
          <Button
            className="add-btn"
            size="small"
            shape="circle"
            icon={<PlusOutlined />}
            onClick={() => addCartCallback(props.id)}
          />
        </div>
      </div>
    </div>
  );
});

export default ProductItem;
