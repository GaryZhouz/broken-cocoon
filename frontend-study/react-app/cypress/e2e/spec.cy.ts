describe('cypress e2e test', () => {
  /**
   * cy.get -> query selector
   * cy.contain -> query text
   */
  beforeEach(() => {
    cy.viewport('iphone-xr');
  });

  it('need show empty cart page when cart is empty', () => {
    cy.visit('http://127.0.0.1:5173/cart');

    // empty cart
    cy.contains('购物车');
    cy.get('.empty-cart').should('exist');
  });

  it('click footer btn should to other page', () => {
    cy.visit('http://127.0.0.1:5173');

    cy.get('.footer a:last-child').click();
    cy.contains('购物车');
    cy.url().should('include', '/cart');
    cy.get('.footer a:last-child span').should('have.class', 'active-btn');

    cy.get('.footer a:first-child').click();
    cy.contains('商城首页');
    cy.url().should('include', '/shop');
    cy.get('.footer a:first-child span').should('have.class', 'active-btn');
  });

  it('add some product to cart when user on shop page', () => {
    cy.visit('http://127.0.0.1:5173');

    // add product to cart
    cy.get('.add-btn').first().click();
    cy.contains('添加购物车成功');
  });

  it('can be update product quantity or delete product', () => {
    cy.visit('http://127.0.0.1:5173');

    // add two product to cart
    cy.get('.add-btn').first().click();
    cy.get('.add-btn').last().click();

    // go to cart page
    cy.get('.footer a:last-child').click();
  });
});
