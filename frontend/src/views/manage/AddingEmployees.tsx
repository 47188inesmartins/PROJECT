import * as React from 'react';
import { useState } from 'react';
import {useParams} from "react-router-dom";
import {UsersService} from "../../Service/UsersService";
import {Layout, LayoutRight} from "../../Pages/Layout";
import {AccessDenied} from "../../Components/AccessDenied";

export function AddingEmployees() {
    const [textBoxes, setTextBoxes] = useState([""]);
    const params = useParams()
    const id = params.id
    const addTextBox = () => {
        setTextBoxes(prevTextBoxes => [...prevTextBoxes, ""]);
    };

    const handleTextBoxChange = (index, value) => {
        const updatedTextBoxes = [...textBoxes];
        updatedTextBoxes[index] = value;
        setTextBoxes(updatedTextBoxes);
    };

    const handleCreate = () => {
        const body = {emails:textBoxes}
        UsersService.addEmployees(id,body)
    };

    const handleLater = () => {
        window.location.href = `/company/${id}/services`;
    }

    const divElem = (
        <div>
            <section className="vh-100 gradient-custom">
                <div className="container py-5 h-100">
                    <div className="row d-flex justify-content-center align-items-center h-100">
                        <div className="col-12 col-md-8 col-lg-6 col-xl-5">
                            <div className="card bg-dark text-white" style={{borderRadius: '1rem'}}>
                                <div className="card-body p-5 text-center">
                                    <div className="mb-md-5 mt-md-4 pb-5">
                                        <h2 className="fw-bold mb-2 text-uppercase">
                                            Add employees to your company
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
                                                <label className="form-label" htmlFor="typeEmailX">employees
                                                    email</label>
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
                                            Add employees
                                        </button>
                                        <br/>
                                        <br/>
                                        <br/>
                                        <button className="btn btn-outline-light btn-lg px-5" type="submit"
                                                onClick={handleLater} >Cancel
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
    return (
        <div>
            <div className="sidebar-left">
                <Layout />
            </div>
            <AccessDenied  company={id} content={divElem} role={['MANAGER']}/>
            <LayoutRight/>
        </div>
    )
}