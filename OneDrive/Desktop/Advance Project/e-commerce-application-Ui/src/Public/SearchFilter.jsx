import React, { useState } from 'react';


const SearchFilter = ({ onFilter, filters }) => {
  const [minPrice, setMinPrice] = useState(filters.minPrice);
  const [maxPrice, setMaxPrice] = useState(filters.maxPrice);
  const [category, setCategory] = useState(filters.category);
  const [availability, setAvailability] = useState(filters.availability);

  const handleFilter = () => {
    onFilter({ minPrice, maxPrice, category, availability });
  };



  return (
    <div className="flex flex-col w-1/3 p-4 bg-white rounded shadow-md">
      <h2 className="text-lg font-bold mb-4">Search Filter</h2>
      <div class="max-w-sm mx-auto lg:max-w-3xl space-y-3 mb-12 lg:mb-16">
    
    <div class="relative flex items-center">
    <label className="text-sm font-bold mb-1" htmlFor="category">
          minimum price:
        </label>
        <input value={minPrice}
          onChange={(e) => setMinPrice(e.target.value)}
           type="range" min="0" max="200000" aria-valuetext="10K contacts/month" aria-label="Pricing Slider"/>
        <h3>{minPrice}</h3>
    </div>
    </div>
    <div class="max-w-sm mx-auto lg:max-w-3xl  mb-12 lg:mb-16">
<div class="relative flex items-center">
<label className="text-sm font-bold mb-1" htmlFor="category">
          maximum price:
        </label>
        <input value={maxPrice}
          onChange={(e) => setMaxPrice(e.target.value)}
           type="range" min="0" max="300000" aria-valuetext="10K contacts/month" aria-label="Pricing Slider"/>
        <h3>{maxPrice}</h3>
    </div>
    </div>


      
      
      <div className="flex flex-col mb-4">
        <label className="text-sm font-bold mb-1" htmlFor="category">
          Category:
        </label>
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
      <div className="flex flex-col mb-4">
        <label className="text-sm font-bold mb-1" htmlFor="availability">
          Availability:
        </label>
        <input
          type="text"
          id="availability"
          value={availability}
          onChange={(e) => setAvailability(e.target.value)}
          className="w-full p-2 pl-10 text-sm text-gray-700"
        />
      </div>
      <button
        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
        onClick={handleFilter}
      >
        Apply Filter
      </button>
    </div>
  );
};

export default SearchFilter;