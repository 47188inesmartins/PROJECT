import * as React from "react";
import {useEffect, useState} from "react";
import {Navigate} from "react-router";
import {Fetch} from "../Utils/useFetch";


interface CompanyInputDto {
    nif: string;
    address: string;
    name: string;
    type: string;
    description: string;
}


export function CreatingCompany(){

    const [companyName, setCompanyName] = useState<string>("");
    const [businessType, setBusinessType] = useState<string>("");
    const [address, setAddress] = useState<string>("");
    const [nif, setNif] = useState<string>("");
    const [description, setDescription] = useState<string>("");
    const [create, setCreate] = useState<boolean>(false)


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
        console.log(create)
        setCreate(true)
        console.log(create)
    };

    if(create){
        console.log("dentro do if")
        setCreate(false)
        return <FetchCreateCompany/>
    }

    function FetchCreateCompany(){
        console.log("company")
        Fetch('/company',
            'POST',
            companyData
        )
        return<></>//  <Navigate to = "/" replace={true}></Navigate>;
    }


    return (
            <div>
                <section className="vh-100 gradient-custom">
                    <div className="container py-5 h-100">
                        <div className="row d-flex justify-content-center align-items-center h-100">
                            <div className="col-12 col-md-8 col-lg-6 col-xl-5">
                                <div className="card bg-dark text-white" style={{borderRadius: '1rem'}}>
                                    <div className="card-body p-5 text-center">
                                        <div className="mb-md-5 mt-md-4 pb-5">
                                            <h2 className="fw-bold mb-2 text-uppercase">Register your company on our
                                                website!</h2>
                                            <div className="form-outline form-white mb-4">
                                                <input
                                                    type="text"
                                                    id="typeEmailX"
                                                    className="form-control form-control-lg"
                                                    value={companyName}
                                                    onChange={(e) => setCompanyName(e.target.value)}
                                                />
                                                <label className="form-label" htmlFor="typeEmailX">Company name</label>
                                            </div>
                                            <div className="form-outline form-white mb-4">
                                                <input
                                                    type="text"
                                                    id="typePasswordX"
                                                    className="form-control form-control-lg"
                                                    value={businessType}
                                                    onChange={(e) => setBusinessType(e.target.value)}
                                                />
                                                <label className="form-label" htmlFor="typePasswordX">Type of
                                                    business</label>
                                            </div>
                                            <div className="form-outline form-white mb-4">
                                                <input
                                                    type="text"
                                                    id="typePasswordX"
                                                    className="form-control form-control-lg"
                                                    value={address}
                                                    onChange={(e) => setAddress(e.target.value)}
                                                />
                                                <label className="form-label" htmlFor="typePasswordX">Address</label>
                                            </div>
                                            <div className="form-outline form-white mb-4">
                                                <input
                                                    type="text"
                                                    id="typePasswordX"
                                                    className="form-control form-control-lg"
                                                    value={nif}
                                                    onChange={(e) => setNif(e.target.value)}
                                                />
                                                <label className="form-label" htmlFor="typePasswordX">NIF</label>
                                            </div>
                                            <div className="form-outline form-white mb-4">
                                                <input
                                                    type="text"
                                                    id="typePasswordX"
                                                    className="form-control form-control-lg"
                                                    value={description}
                                                    onChange={(e) => setDescription(e.target.value)}
                                                />
                                                <label className="form-label"
                                                       htmlFor="typePasswordX">Description</label>
                                            </div>
                                            <form onSubmit={handleCreate}>
                                                <button className="btn btn-outline-light btn-lg px-5" type="submit">
                                                    Create
                                                </button>
                                            </form>
                                            <br/><br/><br/>

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