import React, { useState, useEffect } from 'react';
import axios from 'axios';
import SearchFilter from './SearchFilter';

const Home = () => {
  const [products, setProducts] = useState([]);
  const [filteredProducts, setFilteredProducts] = useState([]);

  useEffect(() => {
    const fetchProducts = async () => {
      const response = await axios.get(`http://localhost:8080/api/v1/products/filter?page=${0}&orderBy=${"DEC"}&sortBy=${"category"}`, {
        headers: {
          'Content-Type': 'application/json'
        },
        withCredentials: true
      });
      console.log(response.data);
      setProducts(response.data); 
      setFilteredProducts(response.data);
    };

    fetchProducts();
  }, []);

  const handleFilter = (filters) => {
    const filtered = products.filter((product) => {
      let isMatch = true;

      if (filters.minPrice && Number(product.
        productPrice
        ) < filters.minPrice) {
        isMatch = false;
      }

      if (filters.maxPrice && Number(product.productPrice) > filters.maxPrice) {
        isMatch = false;
      }

      if (filters.category && product.category !== filters.category) {
        isMatch = false;
      }

      if (filters.availability && product.availability !== filters.availability) {
        isMatch = false;
      }

      return isMatch;
    });

    setFilteredProducts(filtered);
  };

  const toggleSearchFilter = () => {
    setShowSearchFilter(!showSearchFilter);
  };

  const [showSearchFilter, setShowSearchFilter] = useState(false);

  return (
    <div className="container mx-auto px-4 py-8 mt-16">
      <h1 className="text-3xl font-bold mb-4">Products</h1>

      <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded " onClick={toggleSearchFilter}>
        Filter
      </button>

      {showSearchFilter && <div className=' w-full  translate-x-5'> <SearchFilter  onFilter={handleFilter} filters={{ minPrice: 0, maxPrice: 0, category: '', availability: '' }} /></div>}

      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
        {filteredProducts.map((product) => (
          <div key={product.id} className="bg-white shadow-md md:shadow-lg rounded p-4 ">
            <h2 className="text-xl font-bold mb-2">{product.productName}</h2>
 {/* Image container with shadow on hover */}

   <div className="relative group ">
        <img
          src={`http://localhost:8080/api/v1/product/${product.productId}`}
          alt={product.productName}
          className="w-full transition duration-300 scale-75 transform group-hover:scale-105"
        />
        {/* Shadow overlay on hover */}
        <div className="absolute inset-0  opacity-0 group-hover:opacity-25 transition-opacity"></div>
       </div>            
            <p className="text-gray-600 mb-2">Price: ₹{product.productPrice}</p>
            <p className="text-gray-600 mb-2">Description: {product.productDescription}</p>
            <p className="text-gray-600 mb-2">Category: {product.category}</p>
            <p className="text-gray-600">Availability: {product.availabilityStatus}</p>
          </div>
        ))}
      </div>
    </div>

  );
};

export default Home;