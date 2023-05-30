<<<<<<< Updated upstream
import React from 'react';
import { Navbar, Nav, Container } from 'react-bootstrap';

const Sidebar = () => {
  return (
    <Navbar bg="light" expand="lg" className="sidebar">
      <Container>
        <Navbar.Toggle aria-controls="sidebar-nav" />
        <Navbar.Collapse id="sidebar-nav">
          <Nav className="flex-column">
            <Nav.Link href="#home">Home</Nav.Link>
            <Nav.Link href="#about">About</Nav.Link>
            <Nav.Link href="#services">Services</Nav.Link>
            {/* Add more Nav.Link components for additional sidebar items */}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
=======
const Sidebar = () => {

    return(  
            <div className='sidebar'>
                <a>Hola</a>
            </div>
    );

>>>>>>> Stashed changes
};

export default Sidebar;