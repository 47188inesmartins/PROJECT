import * as React from "react";
import {useState} from "react";
import {Fetch} from "../Utils/useFetch";
import {Navigate} from "react-router";
import "../Style/CreatingCompany.css"



interface CompanyInputDto {
    nif: string;
    address: string;
    name: string;
    type: string;
    description: string;
}


export function CreatingServices(){

    const [companyName, setCompanyName] = useState<string>("");
    const [businessType, setBusinessType] = useState<string>("");
    const [address, setAddress] = useState<string>("");
    const [nif, setNif] = useState<string>("");
    const [description, setDescription] = useState<string>("");
    const [create, setCreate] = useState<Boolean>(false)


    const companyData : CompanyInputDto = {
        nif : nif,
        address : address,
        name: companyName,
        type:businessType,
        description: description
    }


    const handleCancel = () => {
        window.location.href = '/'; // Redireciona para a página inicial (home)
    };


    const handleCreate = () => {

        setCreate(true)


    };


    function FetchCreateCompany(){
        const resp = Fetch('/company',
            'POST',
            companyData)
        window.location.href = "/"
        return(<Navigate to = "/" replace={true}></Navigate>);
    }

    const [textBoxes, setTextBoxes] = useState([""]);

    const addTextBox = () => {
        setTextBoxes(prevTextBoxes => [...prevTextBoxes, ""]);
    };

    const handleTextBoxChange = (index, value) => {
        const updatedTextBoxes = [...textBoxes];
        updatedTextBoxes[index] = value;
        setTextBoxes(updatedTextBoxes);
    };

    return (
        <div>
                <section className="vh-100 gradient-custom">
                    <div className="container py-5 h-100">
                        <div className="row d-flex justify-content-center align-items-center h-100">
                            <div className="col-12 col-md-8 col-lg-6 col-xl-5">
                                <div className="card bg-dark text-white" style={{borderRadius: '1rem'}}>
                                    <div className="card-body p-5 text-center">
                                        <div className="mb-md-5 mt-md-4 pb-5">
                                            <h2 className="fw-bold mb-2 text-uppercase">
                                                What services does your company perform?
                                            </h2>
                                            <br/><br/>
                                            {textBoxes.map((value, index) => (
                                                <div className="form-outline form-white mb-4">
                                                    <input
                                                        type="text"
                                                        id="typeEmailX"
                                                        className="form-control form-control-lg"
                                                        value={value}
                                                        onChange={e => handleTextBoxChange(index, e.target.value)}
                                                    />
                                                    <label className="form-label" htmlFor="typeEmailX">Service</label>
                                                </div>
                                            ))}
                                            <button onClick={addTextBox} className="btn btn-dark"
                                                    style={{backgroundColor: 'blueviolet'}}>
                                                <i className="fa fa-plus"></i>
                                            </button>
                                            <br/>
                                            <br/>
                                            <br/>
                                            <button className="btn btn-outline-light btn-lg px-5" type="submit"
                                                    onClick={handleCreate}>
                                                Create
                                            </button>
                                            <br/>
                                            <br/>
                                            <br/>
                                            <button className="btn btn-outline-light btn-lg px-5" type="submit"
                                                    onClick={handleCancel}>Cancel
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
        </div>
    );
}