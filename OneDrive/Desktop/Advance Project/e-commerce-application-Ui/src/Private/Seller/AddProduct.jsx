import axios from 'axios';
import React, { useState } from 'react';

const AddProduct = () => {
  // State variables for product details
  const [productName, setProductName] = useState('');
  const [productDescription, setProductDescription] = useState('');
  const [productPrice, setProductPrice] = useState('');
  const [productQuantity, setProductQuantity] = useState('');
  const [category, setCategory] = useState('');

  // Function to handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log({
      productName,
      productDescription,
      productPrice,
      productQuantity,
      category,
    });
    try {
      let { data: { data } } = await axios.post(`http://localhost:8080/api/v1/products`, {
        productName: productName,
        productDescription: productDescription,
        productPrice: productPrice,
        productQuantity: productQuantity,
        category: category,
      }, {
        headers: {
          "Content-Type": "application/json"
        },
        withCredentials: true
      })
      console.log("after api call", data)
      // Perform validation and submit data to backend or perform other actions
      // Reset form fields
      setProductName('');
      setProductDescription('');
      setProductPrice('');
      setProductQuantity('');
      setCategory('');

    } catch (error) {
      console.log(error)
    }
  };

  return (
    <div className='flex justify-center mt-20'>
      <h4><b>Add Product</b></h4>
      <form onSubmit={handleSubmit}>
        <div className='bg-gray-200 p-4 m-2 rounded-lg' >
          <label>Product Name:</label>
          <input
            type="text"
            value={productName}
            onChange={(e) => setProductName(e.target.value)}
            required
          />
        </div>
        <div className='bg-gray-200 p-4 m-2 rounded-lg'>
          <label>Product Description:</label>
          <textarea
            value={productDescription}
            onChange={(e) => setProductDescription(e.target.value)}
            required
          />
        </div>
        <div className='bg-gray-200 p-4 m-2 rounded-lg'>
          <label>Product Price:</label>
          <input
            type="number"
            value={productPrice}
            onChange={(e) => setProductPrice(e.target.value)}
            required
          />
        </div>
        <div className='bg-gray-200 p-4 m-2 rounded-lg'>
          <label>Product Quantity:</label>
          <input
            type="number"
            value={productQuantity}
            onChange={(e) => setProductQuantity(e.target.value)}
            required
          />
        </div>
        <div className='bg-gray-200 p-4 m-2 rounded-lg'>
          <label>Product Category:</label>
          <select
            value={category}
            onChange={(e) => setCategory(e.target.value)}
            required
          >
            <option value="">-- Select Category --</option>
            <option value="MOBILE">Mobiles</option>
            <option value="PC">PC's</option>
            <option value="STUDY">Study material</option>
            <option value="GAMING">Gaming</option>
            <option value="CAMERA">Camera</option>
            <option value="ACCESSORIES">Accessories</option>
            <option value="PERSONAL_CARE">Personal Care</option>

          </select>
        </div>
        <button type="submit" className=' hover:bg-blue-200 bg-gray-200 p-4 rounded-lg'>Add Product</button>
      </form>
    </div>
  );
};

export default AddProduct;