import React from 'react';
import { Accordion, Button} from 'react-bootstrap';

const Sidebar = () => {

  return (
    <div className='sidebar'>
        <Accordion flush>
            <Accordion.Item eventKey="0">
                <Accordion.Header className="custom-accordion-header">Accordion Item #1</Accordion.Header>
                <Accordion.Collapse eventKey="0">
                    <Accordion.Body>
                        <Button variant="light">Botón 1</Button>
                        <Button variant="light">Botón 1</Button>
                        <Button variant="light">Botón 1</Button>
                    </Accordion.Body>
                </Accordion.Collapse>
            </Accordion.Item>
            <hr/>
            <Accordion.Item eventKey="1">
                <Accordion.Header>Accordion Item #2</Accordion.Header>
                <Accordion.Body>
                
                </Accordion.Body>
            </Accordion.Item>
            <hr/>
        </Accordion>
    </div>
  );
};

export default Sidebar;