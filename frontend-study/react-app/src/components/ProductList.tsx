import '../styles/shop/product-list.scss';
import React, { memo, useState } from 'react';
import ProductItem, { ProductItemProps } from './ProductItem.tsx';

const ProductList: React.FC<{ products: ProductItemProps[] }> = memo((props: { products: ProductItemProps[] }) => {
  const [products] = useState<ProductItemProps[]>(props.products);
  const productItemElements = products?.map((product: ProductItemProps) => (
    <li className="product-item" key={product.id}>
      <ProductItem {...product} />
    </li>
  ));

  return (
    <div className="main-box">
      <ul className="product-list-left">{productItemElements?.splice(0, productItemElements?.length / 2)}</ul>
      <ul className="product-list-right">
        {productItemElements?.splice(Math.max(productItemElements?.length / 2 - 2, 0))}
      </ul>
    </div>
  );
});
export default ProductList;
