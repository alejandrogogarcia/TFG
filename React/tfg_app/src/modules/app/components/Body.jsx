import {BrowserRouter, Route, Routes} from 'react-router-dom';

import Home from './Home';
import UserProfile from '../../users/components/UserProfile';


const Body = () => {

    return ( 
        <div>
            <br/>
            {/* <Home/> */}
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<Home />}/>
                    <Route exact path="/userProfile" element={<UserProfile/>}/>
                </Routes>
            </BrowserRouter>
             
        </div>
    )
}

export default Body;