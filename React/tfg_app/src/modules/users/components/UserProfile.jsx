import { Table } from "react-bootstrap";
import PasswordUpdate from "./PasswordUpdate";


const UserProfile = () => {

    return(
        <div>
            <h2>Información del usuario</h2>

                <Table>
                    <tbody>
                        <tr>
                            <td>Nombre: </td>
                            <td>NombreUsuario </td>
                            <td>Apellido: </td>
                            <td>ApellidoUsuario </td>
                        </tr>
                    </tbody>
                </Table>


            <hr/>
            <PasswordUpdate/>
        </div>
        
    );

}

export default UserProfile;