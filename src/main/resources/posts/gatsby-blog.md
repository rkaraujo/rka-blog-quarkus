---
title: How to create a blog with Gatsby
date: 2020-08-16T23:00:00
description: Hi there, first post here! And the first one is how I configured this blog. As the title say I used Gatsby and this is hosted on Github pages.
---

Hi there, first post here! And the first one is how I configured this blog. As the title say I used [Gatsby](https://www.gatsbyjs.com/) and this is hosted on Github pages.

## Installing and configuring

First of all, you will need installed:

- node.js (at the time of this post I'm using version 12)
- VS Code (or other editor)
- git

After that, to install the Gatsby CLI to generate the project:

```
npm install -g gatsby-cli
```

And to create a new project in a directory called `my-blog`:

```
gatsby new my-blog https://github.com/gatsbyjs/gatsby-starter-blog
```

This command will use a pre-configured starter for blogging websites.

To test if it is working, enter the new directory created and run:

```
npm install
gatsby develop
```

Using your browser you can check if the blog is working accessing the url `http://localhost:8000`.

Open the project in VS Code (or your preferred editor) and edit the file `gatsby-config.js`. In the `siteMetadata` section change the infos with your own blog info.

If you have a Google Analytics account, you set your Google Analytics ID in the section `gatsby-plugin-google-analytics`. You can comment this section if you will not use Google Analytics.

To customize the avatar, copy your image replacing the `profile-pic.jpg` in the `content/assets` directory.

I also customized the text in the file `bio.js` and the footer in the file `layout.js`, both are in the `src/components` directory.

To change the fonts, you can install a theme for the Typography plugin. To see what themes are available check the [typography.js](https://kyleamathews.github.io/typography.js/) webpage. In my case I chose the De Young theme, so I ran the command:

```
npm install typography-theme-de-young
```

After that, edit the file `src/utils/typography.js` to use the new typography.

## Creating posts

The posts go into `content/blog`. Each post has a directory and a index.md inside. The directory will be the url path of the post and the contents are written in Markdown. You can see the examples created to see how to write a new post.

After you create your own post you can delete the examples (I needed to restart my develop server after deleting them).

## Publish to Github pages

To create you Github page repository, follow the instructions on [this website](https://pages.github.com/).

You will need to install the `gh-pages` lib to manage the development mode and the build for publish on Github.

```
npm install gh-pages --save-dev
```

Delete the `.git` directory. We will reconfigure it. The branch `develop` will have the development code and the branch `master` will be the built blog that will be published.

```
rm -rf .git

git init
git checkout -b develop
git add .
git commit -m "Initial blog commit"
git push -u origin develop
```

Be sure that all the above commands were succesful. We will clean the master so that it will have only the built project:

```
git checkout master (for me I got an error saying the branch didn't exist, so I needed to recreate the master branch)
rm -rf *
git add .
git commit -m "Cleaning master"
git checkout develop
```

So back to the develop branch, configure package.json adding this to the `scripts` section:

```
"deploy": "gatsby build --prefix-paths && gh-pages -d public -b master"
```

If you will not use the github default domain and instead will configure your own domain, you can remove the --prefix-paths parameter.

And to deploy your blog, run:

```
npm run deploy
```

For each post you create or modify, you will need to deploy again. And don't forget to commit and push your develop branch to save your work on your github repository.

And that's it. Now access your github page and see your new blog.
