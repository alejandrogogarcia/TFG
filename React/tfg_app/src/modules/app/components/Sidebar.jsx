import React from 'react';
import { Accordion, Button, ListGroup} from 'react-bootstrap';

const Sidebar = () => {

  return (
    <div className='sidebar'>
        <Accordion flush>
            <Accordion.Item eventKey="0">
                <Accordion.Header className="custom-accordion-header">Accordion Item #1</Accordion.Header>
                <Accordion.Collapse eventKey="0">
                    <Accordion.Body className="custom-accordion-body">
                        <ListGroup>
                            <ListGroup.Item action>
                                <a>Prueba</a>
                            </ListGroup.Item>
                            <ListGroup.Item action>
                                <a>Prueba</a>
                            </ListGroup.Item>
                            <ListGroup.Item action>
                                <a>Prueba</a>
                            </ListGroup.Item>
                        </ListGroup>
                        
                    </Accordion.Body>
                </Accordion.Collapse>
            </Accordion.Item>
            <hr class="no-space"/>
            <Accordion.Item eventKey="1">
                <Accordion.Header>Accordion Item #2</Accordion.Header>
                <Accordion.Body>
                
                </Accordion.Body>
            </Accordion.Item>
            <hr class="no-space"/>
        </Accordion>
        <hr class="no-space"/>
        <div className='sidebar-footer'>
            <h5 className='sidebar-footer-text'>TFG - UDC</h5>
        </div>
    </div>
  );
};

export default Sidebar;