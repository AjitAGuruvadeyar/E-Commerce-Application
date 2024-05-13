import React, { useState } from 'react';
import axios from 'axios';

const AddImageForm = () => {
  const [productId, setProductId] = useState('');
  const [imageType, setImageType] = useState('');
  const [imageFile, setImageFile] = useState(null);

  const handleProductIdChange = (e) => {
    setProductId(e.target.value);
  };

  const handleImageTypeChange = (e) => {
    setImageType(e.target.value);
  };

  const handleImageChange = (e) => {
    setImageFile(e.target.files[0]);
  };

  const handleImageUpload = async () => {
    const formData = new FormData();
    formData.append('productId', productId);
    formData.append('imageType', imageType);
    formData.append('image', imageFile);

    try {
        await axios.post(`http://localhost:8080/api/v1/products/${productId}/image-type/${imageType}/images`, formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            },withCredentials:true
          });
          
      alert('Image uploaded successfully');
    } catch (error) {
      console.error('Error uploading image:', error);
      alert('Failed to upload image');
    }
  };

  return (
    <div className="max-w-md mx-auto bg-white p-8 rounded shadow-lg mt-8">
      <h2 className="text-2xl font-semibold mb-4">Add Image</h2>
      <div className="mb-4">
        <label htmlFor="productId" className="block text-sm font-medium text-gray-700">
          Product ID:
        </label>
        <input
          type="text"
          id="productId"
          value={productId}
          onChange={handleProductIdChange}
          className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
        />
      </div>
      <div className="mb-4">
        <label htmlFor="imageType" className="block text-sm font-medium text-gray-700">
          Image Type:
        </label>
        <select
          type="text"
          id="imageType"
          value={imageType}
          onChange={handleImageTypeChange}
          className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
        >
            <option value="">-- Select Image Type --</option>
            <option value="COVER">COVER</option>
            <option value="NORMAL">NORMAL</option>
            

          </select>

      </div>
      <div className="mb-4">
        <label htmlFor="image" className="block text-sm font-medium text-gray-700">
          Select Image:
        </label>
        <input
          type="file"
          id="image"
          onChange={handleImageChange}
          className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
        />
      </div>
      <button
        onClick={handleImageUpload}
        className="bg-indigo-500 text-white px-4 py-2 rounded hover:bg-indigo-600 focus:outline-none focus:ring-2 focus:ring-indigo-500"
      >
        Upload Image
      </button>
    </div>
  );
};

export default AddImageForm;
