class App {

    static DOMAIN = location.origin;

    static BASE_URL_PRODUCT = this.DOMAIN + "/api/products";

    static BASE_URL_CLOUD_IMAGE = "https://res.cloudinary.com/c0721k1/image/upload";
    static BASE_URL_CLOUD_VIDEO = "https://res.cloudinary.com/c0721k1/video/upload";
    static BASE_SCALE_IMAGE = "c_limit,w_150,h_100,q_100";

    static ERROR_400 = "Giao dịch không thành công, vui lòng kiểm tra lại dữ liệu.";
    static ERROR_403 = "Access Denied! You are not authorized to perform this function.";
    static ERROR_404 = "An error occurred. Please try again later!";
    static ERROR_500 = "Lưu dữ liệu không thành công, vui lòng liên hệ quản trị hệ thống.";
    static SUCCESS_CREATED = "Successful data generation !";
    static SUCCESS_UPDATED = "Data update successful !";
    static SUCCESS_DELETED = "Delete product successfully !";

    static showDeleteConfirmDialog() {
        return Swal.fire({
            icon: 'warning',
            text: 'Are you sure to delete the selected product ?',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, please delete this client !',
            cancelButtonText: 'Cancel',
        })
    }

    static showSuccessAlert(t) {
        Swal.fire({
            icon: 'success',
            title: t,
            position: 'top-end',
            showConfirmButton: false,
            timer: 1500
        })
    }

    static showErrorAlert(t) {
        Swal.fire({
            icon: 'error',
            title: 'Warning',
            text: t,
        })
    }
}

class Category {
    constructor(id, name) {
        this.id = id;
        this.name = name;
    }
}

class CategoryGroup {
    constructor(id, name, category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }
}

class Product {
    constructor(id, name, slug, author, quantity, publishingCompany,
                publicationDate, page, vote, comment, price,
                percentageDiscount, discountAmount, lastPrice,
                rewardPoint, views, description, avatar, categoryGroup) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.author = author;
        this.quantity = quantity;
        this.publishingCompany = publishingCompany;
        this.publicationDate = publicationDate;
        this.page = page;
        this.vote = vote;
        this.comment = comment;
        this.price = price;
        this.percentageDiscount = percentageDiscount;
        this.discountAmount = discountAmount;
        this.lastPrice = lastPrice;
        this.rewardPoint = rewardPoint;
        this.views = views;
        this.description = description;
        this.avatar = avatar;
        this.categoryGroup = categoryGroup;
    }
}

class CartDetail {
    constructor(id, cart, product, quantity) {
        this.id = id;
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }
}

class CartDetailDTO {
    constructor(id, cartId, productId, name, lastPrice, quantity) {
        this.id = id;
        this.cartId = cartId;
        this.productId = productId;
        this.name = name;
        this.lastPrice = lastPrice;
        this.quantity = quantity;
    }
}

class Cart {
    constructor(id, customer) {
        this.id = id;
        this.customer = customer;
    }
}

class Customer {
    constructor(id, username, password, name, address, phone, email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }
}