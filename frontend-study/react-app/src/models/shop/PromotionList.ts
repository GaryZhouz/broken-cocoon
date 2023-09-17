export interface Promotion {
  promotionName: string;
  promotionDesc: string;
  promotionType: PromotionType;
}

export enum PromotionType {
  NINE_DISCOUNT,
  FULL_1000_OFF_150,
  FULL_3000_OFF_500,
  NONE_PROMOTION,
}

export const ALL_PROMOTION: Promotion[] = [
  {
    promotionName: '整体九折',
    promotionDesc: '所选商品总金额打九折',
    promotionType: PromotionType.NINE_DISCOUNT,
  },
  {
    promotionName: '满1000减150',
    promotionDesc: '任选商品总金额每满1000减150',
    promotionType: PromotionType.FULL_1000_OFF_150,
  },
  {
    promotionName: '满3000减500',
    promotionDesc: '任选商品总金额每满3000减500',
    promotionType: PromotionType.FULL_3000_OFF_500,
  },
  {
    promotionName: '不使用优惠',
    promotionDesc: '不优惠也罢',
    promotionType: PromotionType.NONE_PROMOTION,
  },
];
