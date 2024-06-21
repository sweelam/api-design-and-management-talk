import React, { useEffect, useState } from 'react';
import { v4 as uuidv4 } from 'uuid';

function Profile() {
    const [profile, setProfile] = useState('loading...');
    const [posts, setPosts] = useState([]);


    useEffect(() => {
        fetch('http://localhost:8088/api/profiles/6652a3aef8e0f9652be33012')
            .then(response => response.json())
            .then(data => {
                setProfile(data);
                setPosts(data.posts)
            })
            .catch(err => {
                console.error("Something went wrong!", err);
            })
    }, []);

    return (
        <div className="profile-section">
            <div className="header">
                <h1>Mock Social Media Platform</h1>
            </div>

            <div className="profile-section">
                <div className="profile-image"></div>
                <h2>Username</h2>
                <p>{profile?.username}</p>
            </div>

            <div className="post-section">
                {
                    posts?.map(p => <div className="post-image" key={uuidv4()}> {p.text} </div>)
                }
                <p>Here is a sample post text. This could be an interesting update, question, or insight shared by the user.</p>
                <div className="interaction-buttons">
                    <button className="like">Like</button>
                    <button className="comment">Comment</button>
                </div>
                <div className="comment-section">
                    <div className="comment">Sample comment 1</div>
                    <div className="comment">Sample comment 2</div>
                </div>
            </div>

        </div>
    );
}

export default Profile;
