import {BrowserRouter, Route, Routes} from 'react-router-dom';

import Home from './Home';
import UserProfile from '../../users/components/UserProfile';
import ManageProducts from '../../products/components/ManageProducts';
import ProductCard from '../../products/components/ProductCard';
import ProductCreationForm from '../../products/components/ProductCreationForm';
import ProductEditForm from '../../products/components/ProductEditForm';


const Body = () => {

    return ( 
        <div className='body'>
            {/* <Home/> */}
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<Home />}/>
                    <Route exact path="/userProfile" element={<UserProfile/>}/>
                    <Route exact path="/manageProducts" element={<ManageProducts/>}/>
                    <Route exact path="/product" element={<ProductCard/>}/>
                    <Route exact path="/createProduct" element={<ProductCreationForm/>}/>
                    <Route exact path="/productEditForm" element={<ProductEditForm/>}/>

                    
                </Routes>
            </BrowserRouter>
             
        </div>
    )
}

export default Body;