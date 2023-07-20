import * as React from 'react';
import { useState } from 'react';
import '../Style/SearchBar.css';
import {
    MDBCard,
    MDBContainer,
    MDBCol,
    MDBRow,
} from 'mdb-react-ui-kit';
import { Fetch } from '../Utils/useFetch';
import { Layout, LayoutRight } from './Layout';
import { LoggedInContextCookie } from "../views/Authentication/Authn";
import {Dropdown, DropdownButton} from "react-bootstrap";


export function Home() {

    const context = React.useContext(LoggedInContextCookie).loggedInState
    const role = context.role

    const hasManager = role.some((user) => user.role === "MANAGER");
    const hasEmployee = role.some((user) => user.role === "EMPLOYEE");

    function getQueryParam(param) {
        const searchParams = new URLSearchParams(window.location.search);
        return searchParams.get(param);
    }

    const searchValue = getQueryParam('search');
    const page = Number.isNaN(parseInt(getQueryParam('page'))) ? 0 : parseInt(getQueryParam('page'))
    const size = Number.isNaN(parseInt(getQueryParam('size'))) ? 3 : parseInt(getQueryParam('size'))
    const distance = Number.isNaN(parseInt(getQueryParam('distance'))) ? 10 : parseInt(getQueryParam('distance'))

    const [selectedDistance, setSelectedDistance] = useState(distance);

    let url;
    if (searchValue !== null) {
        url = `/company/search?search=${searchValue}`;
    } else {
        const getPage: number = Number.isNaN(parseInt(String(page))) ? 0 : parseInt(String(page));
        const getSize: number = Number.isNaN(parseInt(String(size))) ? 3 : parseInt(String(size));
        url = `/?page=${getPage}&size=${getSize}`;
        if (selectedDistance !== 0) {
            url += `&distance=${selectedDistance}`;
        }
    }

    console.log("url", url)

    const response = Fetch(url, 'GET');

    const goToNextPage = () => {
        const currentURL = new URL(window.location.href);
        const searchParams = currentURL.searchParams;
        searchParams.set("page", `${page+1}`);
        const newURL = `${currentURL.pathname}?${searchParams.toString()}`;
        window.location.href = newURL
    };


    const goToPreviousPage = () => {
        const currentURL = new URL(window.location.href);
        const searchParams = currentURL.searchParams;
        searchParams.set("page", `${page-1}`);
        const newURL = `${currentURL.pathname}?${searchParams.toString()}`;
        window.location.href = newURL
    };


    const [searchTerm, setSearchTerm] = useState('');
    const [submit, setSubmit] = useState(false);

    const handleSubmit = (event) => {
        event.preventDefault();
        setSubmit(true);
    };

    const handleChange = (e) => {
        setSearchTerm(e.target.value);
    };

    function FetchSearch() {
        const currentURL = new URL(window.location.href);
        currentURL.searchParams.set("search", searchTerm);
        window.location.href = currentURL.toString();
        return <></>;
    }

    const handleFetchDistance = (distance) => {
        const currentURL = new URL(window.location.href);
        currentURL.searchParams.set("distance", distance);
        window.location.href = currentURL.toString();
    };


    return (
        <div style={{ display: 'flex' }}>
            <div className="sidebar-left">
                <Layout />
            </div>
            <div
                className="list-container"
                style={{
                    flex: 1,
                    marginLeft: '200px',
                    marginRight: '200px',
                    overflowY: 'auto',
                }}
            >
                {!response.response ? (
                    <p>Loading...</p>
                ) : (
                    <div>
                        <div className="container">
                            <div>
                                {submit ? (
                                    <div>
                                        <FetchSearch />
                                    </div>
                                ) : (
                                    <form onSubmit={handleSubmit}>
                                        <div className="input-group">
                                            <input
                                                type="text"
                                                className="form-control"
                                                placeholder="Search..."
                                                value={searchTerm}
                                                onChange={handleChange}
                                            />
                                            <DropdownButton title={ `Distance: ${selectedDistance}`} className="dropdown-button">
                                                <Dropdown.Item onClick={() => handleFetchDistance(10.0)}>10 KM</Dropdown.Item>
                                                <Dropdown.Item onClick={() => handleFetchDistance(20.0)}>20 KM</Dropdown.Item>
                                                <Dropdown.Item onClick={() => handleFetchDistance(30.0)}>30 KM</Dropdown.Item>
                                                <Dropdown.Item onClick={() => handleFetchDistance(40.0)}>40 KM</Dropdown.Item>
                                            </DropdownButton>
                                        </div>
                                    </form>
                                )}
                            </div>
                        </div>
                        {response.response?
                            <>
                        {response.response.content.length === 0 ? (
                            <MDBContainer className="py-5">
                                <MDBCard className="px-3 pt-3" style={{ maxWidth: '100%' }}>
                                    <div>
                                        <a>No matches found</a>
                                    </div>
                                    <br />
                                </MDBCard>
                            </MDBContainer>
                        ) : (
                            <MDBContainer className="py-5">
                                <MDBCard className="px-3 pt-3" style={{ maxWidth: '100%' }}>
                                    <div>
                                        {response.response ?
                                            <>
                                                {response.response.content.map((object: any) => (
                                                    <a
                                                        key={object.id}
                                                        href={`/company/${object.id}`}
                                                        className="text-dark"
                                                    >
                                                        <MDBRow className="mb-4 border-bottom pb-2">
                                                            <MDBCol size="3">
                                                                {object.path[0]?
                                                                <img
                                                                    src={`data:image/jpeg;base64,${object.path[0]}`}
                                                                    className="img-fluid shadow-1-strong rounded"
                                                                    alt="Hollywood Sign on The Hill"
                                                                    style={{
                                                                        width: '100%',
                                                                        height: '150px',
                                                                        objectFit: 'cover',
                                                                    }}
                                                                />:<img
                                                                        src={"frontend/public/images.png"}
                                                                        className="lol"
                                                                        alt="Hollywood Sign on The Hill"
                                                                        style={{
                                                                            width: '100%',
                                                                            height: '150px',
                                                                            objectFit: 'cover',
                                                                        }}
                                                                    />
                                                                }
                                                            </MDBCol>
                                                            <MDBCol size="9">
                                                                <p className="mb-2">
                                                                    <strong className="company-name">{object.name}</strong>
                                                                </p>
                                                                <p>
                                                                    <u>Location: {object.address}</u>
                                                                </p>
                                                                <p>
                                                                    <u>Company description:{object.description}</u>
                                                                </p>
                                                            </MDBCol>
                                                        </MDBRow>
                                                    </a>
                                                ))}</> : <></>
                                        }</div>
                                    <div className="pagination-buttons-container">
                                        <button
                                            className="btn btn-primary"
                                            onClick={goToPreviousPage}
                                            disabled={page <= 0}
                                        >
                                            Previous
                                        </button>
                                        <button
                                            className="btn btn-primary"
                                            onClick={goToNextPage}
                                            disabled={response.response.totalPages <= page+1}
                                        >
                                            Next
                                        </button>
                                    </div>
                                </MDBCard>
                            </MDBContainer>
                        )}</>: <></>
                        }
                    </div>
                )}
            </div>
            {hasManager || hasEmployee?
                <LayoutRight />
                :
                <></>
            }
        </div>
    );
}
