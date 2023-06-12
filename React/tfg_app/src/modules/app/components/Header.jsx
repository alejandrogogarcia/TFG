import { FormattedMessage } from "react-intl";
import { FaUserCircle } from 'react-icons/fa';

import { useDispatch, useSelector } from 'react-redux';


import {Navbar, Dropdown} from 'react-bootstrap';
import Container from 'react-bootstrap/Container';
import { logoutuser } from "../../users/store/userSlice";


const Header = () => {

    const dispatch = useDispatch();

    const user = useSelector((state) => state.user);

    return(
        <div className="header">
            <Navbar bg="dark" expand="xl">
                <Container fluid>
                    <Navbar.Brand href="/">
                        <FormattedMessage id="project.app.Header.AppName"/>
                    </Navbar.Brand>
                    {//user.token ?
                    true ? 
                        <div className="header-align-right">
                            <Dropdown  align="end">
                                <Dropdown.Toggle className="header-button" variant="link" id="dropdown-basic">
                                    <FaUserCircle className="header-icon"/>
                                </Dropdown.Toggle>
                                <Dropdown.Menu variant="dark">
                                    <Dropdown.Item href="#/action-1">Mi Perfil</Dropdown.Item>
                                    <Dropdown.Item href="#/action-2">Ajustes</Dropdown.Item>
                                    <Dropdown.Divider />
                                    <Dropdown.Item href="/" onClick={(e)=> {dispatch(logoutuser())}}>Cerrar Sesión</Dropdown.Item>
                                </Dropdown.Menu>
                            </Dropdown>
                            <Navbar.Text className="header-welcome">
                                {user.name}
                            </Navbar.Text>
                            
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