import React from 'react'
import Explore from '../Private/Customer/Explore'
import WishList from '../Private/Customer/Wishlist'
import Cart from '../Private/Customer/Cart'
import VerifyOTP from '../Public/verifyOTP'
import Home from '../Public/Home'
import Register from '../Public/Register'
import Login from '../Public/Login'

import EditProfile from '../Private/Commen/EditProfile'
import {Route,Routes} from 'react-router-dom'
import App from '../App'

import Orders from '../Private/Seller/Orders'
//import { links } from './links'
import {useAuth} from "../Auth/AuthProvider"
import Logout from '../Private/Commen/Logout'
import Address from '../Public/Address'
import Contact from '../Private/Commen/Contact'
import MyProfile from '../Private/Commen/MyProfile'
import ManageAddress from '../Private/Commen/ManageAddress'
import SellerDashboard from '../Private/Seller/SellerDashboard'
import AddProduct from '../Private/Seller/AddProduct'
import AddImage from '../Private/Seller/AddImage'

const links=[
    {
        path:"/customer/register",
        element:<Register ROLE= {"CUSTOMER"}/>
    },
    {
        path:"/seller/register",
        element:<Register ROLE= {"SELLER"}/>
    }
]

const AllRoutes = () => {
    

    const {user}=useAuth()
    const {role,authenticated}=user;
    let routes=[]
    
    if(authenticated){
        
        (role==="SELLER")?
        routes.push(
            <Route key={'1'} path='/seller-dashboard' element={<SellerDashboard/>}/>,
            <Route key={'2'} path='/add-product' element={<AddProduct/>}/>,
            <Route key={'21'} path='/orders' element={<Orders/>}/>,
            <Route key={'22'} path='/add-image' element={<AddImage/>}/>

            
        ):((role==="CUSTOMER")&&
        routes.push(<Route key={'1'} path='/explore' element={<Explore/>}/>,
        <Route key={'3'}  path='/cart' element={<Cart/>}/>,
        <Route key={'4'}  path='/wishlist' element={<WishList/>}/>
        ))
        routes.push(
            <Route key={'5'} path='/' element={<Home/>}/>,
            <Route  path='/address' element={<Address/>}/>,
            <Route  path='/contact' element={<Contact/>}/>,
            <Route  path='/myProfile' element={<MyProfile/>}/>,
            <Route  path='/manageAddress' element={<ManageAddress/>}/>,
            <Route key={'7'} path='/edit-profile' element={<EditProfile/>}/>,
            <Route key={'13'} path='/logout' element={<Logout/>}/>
        )
    }
        else{
            routes.push(
                <Route key={'8'} path='/' element={<Home/>}/>,
                <Route key={'9'} path='/login' element={<Login/>}/>,
                <Route key={'12'} path='/verify-otp' element={<VerifyOTP/>}/>,
                
                links.map((nav,i)=>{
                    return (
                        <Route key={'12'} path={nav.path} element={nav.element}/>
                    )
                })
               
            )
           
        }
  return <Routes> <Route path='/' element={<App/>}>{routes}</Route></Routes>
}

export default AllRoutes