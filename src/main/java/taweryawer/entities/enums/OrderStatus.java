package taweryawer.entities.enums;

public enum OrderStatus {
    NEW {
        @Override
        public String toString() {
            return "Ще не оформлений";
        }
    },
    WAITING {
        @Override
        public String toString() {
            return "Очікує підтвердження оператором⏳";
        }
    },
    CONFIRMED {
        @Override
        public String toString() {
            return "Підтверджений оператором✅";
        }
    },
    CANCELED {
        @Override
        public String toString() {
            return "Скасований оператором❌";
        }
    },
    AWAITING_LIQPAY_PAYMENT {
        @Override
        public String toString() {
            return "Очікується оплата через Liqpay";
        }
    },
    CONFIRMED_LIQPAY_PAYMENT {
        @Override
        public String toString() {
            return "Замовлення оплачено за допомогою Liqpay, очікує підтвердження оператором.";
        }
    }
}
