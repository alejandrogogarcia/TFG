import { FormattedMessage } from "react-intl";

const  Footer = () => (

    <div className="footer">
        <hr/>
        <footer>
            <p  className="footer_text">
                <FormattedMessage id="project.app.Footer.text"/>
            </p>
        </footer>
    </div>

)

export default Footer;