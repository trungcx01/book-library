function handleFileChange(event) {
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.onload = function(e) {
        const imagePreview = document.getElementById("imagePreview");
        imagePreview.innerHTML = `<img src="${e.target.result}" alt="Uploaded Image" class="img-fluid" alt="Uploaded Image" style="max-height: 100%; max-width: 100%; object-fit: contain;">`;
    };
    reader.readAsDataURL(file);
}

function editData(event) {
    event.preventDefault(); // Ngăn form tự động submit

    var errorMessages = document.querySelectorAll('.error-message')
     for (var i = 0; i < errorMessages.length; i++) {
        errorMessages[i].style.display = 'none';
    }

    var label = document.getElementById('lbl-button');
    label.classList.replace("btn-secondary", "btn-primary");

    var clear = document.getElementById('clear');
    clear.classList.replace("btn-dark", "btn-primary");
    clear.disabled = false;

    var submitLabel = document.getElementById('submit-lbl');
    submitLabel.innerHTML = "<b>Save</b>";

    var submitButton = document.getElementById('submit-btn');
    submitButton.setAttribute("type", "submit");
    submitButton.onclick = null;

     var inputs = document.getElementsByClassName('data');
    for (var i = 0; i < inputs.length; i++) {
        inputs[i].disabled = false;
    }

}


function clearImage(event) {
    event.preventDefault();
    const imagePreview = document.getElementById('imagePreview');
    imagePreview.innerText = '';
    const imageInput = document.getElementById('image');
    imageInput.value = '';
    const empty = document.getElementById('empty');
    empty.setAttribute("value", "empty");
}


     function selectStar(star) {
    let stars = document.querySelectorAll('.rating i');
    let ratingInput = document.getElementById('rating-input');

    for (let i = 0; i < stars.length; i++) {
      if (i < star) {
        stars[i].classList.add('fas');
        stars[i].classList.remove('far');
      } else {
        stars[i].classList.remove('fas');
        stars[i].classList.add('far');
      }
    }

    ratingInput.value = star;
  }


function addToCart() {
  // Lấy thông tin sản phẩm
  const productName = document.querySelector('.pname').textContent;
  const quantity = parseInt(document.getElementById('quantity').value);
  const price = parseInt(document.querySelector('.priceInput').value);
  const imageUrl = document.querySelector('.big-img img').getAttribute("src");

  // Kiểm tra xem Local Storage đã được khởi tạo chưa
  let cartItems = JSON.parse(localStorage.getItem('cartItems'));
  if (!cartItems) {
    cartItems = [];
  }

  // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
  const existingProduct = cartItems.find(item => item.name === productName);

  if (existingProduct) {
    // Sản phẩm đã có trong giỏ hàng, cộng dồn quantity và cập nhật total
    existingProduct.quantity += quantity;
    existingProduct.total = existingProduct.quantity * existingProduct.price;
  } else {
    // Sản phẩm chưa có trong giỏ hàng, thêm mới vào mảng
    const product = {
      name: productName,
      quantity: quantity,
      price: price,
      image: imageUrl,
      total: quantity * price
    };
    cartItems.push(product);
  }

  // Cập nhật Local Storage
  localStorage.setItem('cartItems', JSON.stringify(cartItems));

  // Cập nhật số lượng sản phẩm trong giỏ hàng
  const itemCount = cartItems.length;
  document.querySelector('.item-count').textContent = itemCount;
}

function updateItemCount() {
  let cartItems = JSON.parse(localStorage.getItem('cartItems'));
  const itemCount = cartItems ? cartItems.length : 0;
  document.querySelector('#cartItemCount').textContent = itemCount;
}

document.addEventListener('DOMContentLoaded', function() {
  updateItemCount();
});


function filterBooks() {
    const sortBy = document.getElementsByClassName('sortBySelect')[0].value;
    console.log(sortBy);
    const category = /*[[${category}]]*/ '';
    const keyword = /*[[${keyword}]]*/ '';  //var keyword = /*[[${keyword}]]*/ 'default-value'; // Nếu keyword không tồn tại, gán giá trị mặc định
    console.log(keyword);
    console.log(category);
    fetch(`/sort?category=` + category + `&keyword=` + keyword + `&sortBy=` + sortBy)
        .then(response => response.text())
        .then(data => {
            const bookList = document.getElementById("bookList");
            bookList.innerHTML = data;
        })
        .catch(error => {
            console.log('Lỗi: ' + error);
        });
}

