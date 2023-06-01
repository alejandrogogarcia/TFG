import { FormattedMessage } from "react-intl";
import { FaUserCircle } from 'react-icons/fa';

import { useSelector } from 'react-redux';


import {Navbar, Dropdown} from 'react-bootstrap';
import Container from 'react-bootstrap/Container';


const Header = () => {

    const user = useSelector((state) => state.user);

    return(
        <div className="header">
            <Navbar bg="dark" expand="xl">
                <Container fluid>
                    <Navbar.Brand href="/">
                        <FormattedMessage id="project.app.Header.AppName"/>
                    </Navbar.Brand>
                    {user.token ? 
                        <div className="header-align-right">
                            <Navbar.Text className="header-welcome">
                                Bienvenido, {user.name}
                            </Navbar.Text>
                            <Dropdown>
                                <Dropdown.Toggle className="header-button" variant="link" id="dropdown-basic">
                                    <FaUserCircle className="header-icon"/>
                                </Dropdown.Toggle>
                            </Dropdown>
                            
                        </div>
                    :
                        <div/>
                    }

                </Container>
            </Navbar>
        </div>
    );
}

export default Header;