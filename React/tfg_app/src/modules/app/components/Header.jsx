import { FormattedMessage } from "react-intl";

import Navbar from 'react-bootstrap/Navbar';
import Container from 'react-bootstrap/Container';


const Header = () => {

    return(
        <>
            <Navbar bg="dark" expand="lg">
                <Container fluid>
                    <Navbar.Brand href="#">
                        <FormattedMessage id="project.app.Header.AppName"/>
                    </Navbar.Brand>
                    <div className='search-box'>
                        <input placeholder="Search.." />
                        <i type="submit"/>
                    </div>
                </Container>
            </Navbar>
        </>
    );
}

export default Header;