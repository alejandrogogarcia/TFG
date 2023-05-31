import { FormattedMessage } from "react-intl";
import { FaUserCircle } from 'react-icons/fa';


import Navbar from 'react-bootstrap/Navbar';
import Container from 'react-bootstrap/Container';


const Header = () => {

    return(
        <div className="header">
            <Navbar bg="dark" expand="xl">
                <Container fluid>
                    <Navbar.Brand href="/">
                        <FormattedMessage id="project.app.Header.AppName"/>
                    </Navbar.Brand>
                </Container>
            </Navbar>
        </div>
    );
}

export default Header;