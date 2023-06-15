import { Button, Form, Modal } from "react-bootstrap"; 

const ProductEditStock = ({ show, setShow}) => {

    const handleClose = () => {
        setShow(false);
      };

    <div>
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Edit Produc Stock</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group>
                        <Form.Label>Sotck units</Form.Label>
                    <Form.Control
                        type="numbre"
                        placeholder="0"
                        autoFocus
                    />
                    </Form.Group>
                </Form>
            </Modal.Body>
            <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
                Cancel
            </Button>
            <Button variant="primary" onClick={handleClose}>
                Remove Stock
            </Button>
            <Button variant="primary" onClick={handleClose}>
                Add Stock
            </Button>
            </Modal.Footer>
        </Modal>

    </div>
}

export default ProductEditStock;