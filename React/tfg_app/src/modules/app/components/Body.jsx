import {BrowserRouter, Route, Routes} from 'react-router-dom';

import Home from './Home';
import PasswordUpdate from '../../users/components/PasswordUpdate';


const Body = () => {

    return ( 
        <div>
            <br/>
            {/* <Home/> */}
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<Home />}/>
                    <Route exact path="/userProfile" element={<PasswordUpdate/>}/>
                </Routes>
            </BrowserRouter>
             
        </div>
    )
}

export default Body;