import React from 'react';
import { Accordion, Button, ListGroup} from 'react-bootstrap';
import {FormattedMessage} from 'react-intl';

const Sidebar = () => {

  return (
    <div className='sidebar'>
        <Accordion flush>
            <Accordion.Item eventKey="0">
                <Accordion.Header className="custom-accordion-header">
                    <FormattedMessage id="app.sidebar.products"/> 
                </Accordion.Header>
                <Accordion.Collapse eventKey="0">
                    <Accordion.Body className="custom-accordion-body">
                        <ListGroup>
                            <ListGroup.Item action>
                                <a><FormattedMessage id="app.sidebar.products.addProduct"/> </a>
                            </ListGroup.Item>
                            <ListGroup.Item action>
                                <a><FormattedMessage id="app.sidebar.products.manageProducts"/> </a>
                            </ListGroup.Item>
                        </ListGroup>    
                    </Accordion.Body>
                </Accordion.Collapse>
            </Accordion.Item>
            <hr class="no-space"/>
            <Accordion.Item eventKey="1">
                <Accordion.Header className="custom-accordion-header">
                    <FormattedMessage id="app.sidebar.clients"/> 
                </Accordion.Header>   
                <Accordion.Collapse eventKey="1">
                    <Accordion.Body className="custom-accordion-body">
                        <ListGroup>
                            <ListGroup.Item action>
                                <a><FormattedMessage id="app.sidebar.clients.searchClient"/> </a>
                            </ListGroup.Item>
                            <ListGroup.Item action>
                                <a><FormattedMessage id="app.sidebar.clients.manageClients"/> </a>
                            </ListGroup.Item>
                        </ListGroup> 
                    </Accordion.Body>
                </Accordion.Collapse>
            </Accordion.Item>
            <hr class="no-space"/>
            <Accordion.Item eventKey="2">
                <Accordion.Header className="custom-accordion-header">
                    <FormattedMessage id="app.sidebar.deliveryNotes"/> 
                </Accordion.Header>                
                <Accordion.Collapse eventKey="2">
                    <Accordion.Body className="custom-accordion-body">
                        <ListGroup>
                            <ListGroup.Item action>
                                <a><FormattedMessage id="app.sidebar.deliveryNotes.createDeliveryNote"/> </a>
                            </ListGroup.Item>
                            <ListGroup.Item action>
                                <a><FormattedMessage id="app.sidebar.deliveryNotes.manageDeliveryNotes"/> </a>
                            </ListGroup.Item>
                        </ListGroup> 
                    </Accordion.Body>
                </Accordion.Collapse>
            </Accordion.Item>
            <hr class="no-space"/>
            <Accordion.Item eventKey="3">
                <Accordion.Header className="custom-accordion-header">
                    <FormattedMessage id="app.sidebar.invoices"/> 
                </Accordion.Header>                
                <Accordion.Collapse eventKey="3">
                    <Accordion.Body className="custom-accordion-body">
                        <ListGroup>
                            <ListGroup.Item action>
                                <a><FormattedMessage id="app.sidebar.invoices.createInvoice"/> </a>
                            </ListGroup.Item>
                            <ListGroup.Item action>
                                <a><FormattedMessage id="app.sidebar.invoices.invoiceDeliveryNotes"/> </a>
                            </ListGroup.Item>
                            <ListGroup.Item action>
                                <a><FormattedMessage id="app.sidebar.invoices.mangeInvoices"/> </a>
                            </ListGroup.Item>
                        </ListGroup> 
                    </Accordion.Body>
                </Accordion.Collapse>
            </Accordion.Item>
            <hr class="no-space"/>
            <Accordion.Item eventKey="4">
                <Accordion.Header className="custom-accordion-header">
                    <FormattedMessage id="app.sidebar.users"/> 
                </Accordion.Header>                
                <Accordion.Collapse eventKey="4">
                    <Accordion.Body className="custom-accordion-body">
                        <ListGroup>
                            <ListGroup.Item action>
                                <a><FormattedMessage id="app.sidebar.users.createUser"/> </a>
                            </ListGroup.Item>
                            <ListGroup.Item action>
                                <a><FormattedMessage id="app.sidebar.users.manageUsers"/> </a>
                            </ListGroup.Item>
                        </ListGroup> 
                    </Accordion.Body>
                </Accordion.Collapse>
            </Accordion.Item>
            <hr class="no-space"/>
        </Accordion>
        <hr class="no-space"/>
        <div className='sidebar-footer'>
            <h5 className='sidebar-footer-text'>
                <FormattedMessage id="app.sidebar.footer"/> 
            </h5>
        </div>
    </div>
  );
};

export default Sidebar;