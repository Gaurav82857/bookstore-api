package com.bookstore.model.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class BookQuantityDto {

        private String ISBN;
        private Integer quantity=1;

        public String getISBN() {
			return ISBN;
		}

		public void setISBN(String iSBN) {
			ISBN = iSBN;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQty(Integer quantity) {
			this.quantity = quantity;
		}

		@Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                BookQuantityDto bookQuantityDto = (BookQuantityDto) o;
                return Objects.equals(ISBN, bookQuantityDto.ISBN);
        }

        @Override
        public int hashCode() {
                return Objects.hash(ISBN);
        }
}