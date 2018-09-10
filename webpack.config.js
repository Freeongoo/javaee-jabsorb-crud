
const path = require('path');
const webpack = require('webpack');
const ExtractTextPlugin = require("extract-text-webpack-plugin");
const CopyWebpackPlugin = require('copy-webpack-plugin');
const autoprefixer = require('autoprefixer');
const CleanWebpackPlugin = require('clean-webpack-plugin');
const WebpackNotifierPlugin = require('webpack-notifier');
const ManifestPlugin = require('webpack-assets-manifest');

let isProd = process.env.NODE_ENV === 'production'; //true or false

const extractSass = new ExtractTextPlugin('[name]');

module.exports = env => {

    let dist = './src/main/webapp';
    let srcDir = './src/main/resources/static';

    let plugins = [
        new CleanWebpackPlugin([
                'css/*',
                'img/*',
                'fonts/*',
                'js/*',
            ],
            {
                root:     __dirname + '/' + dist,
                verbose:  true,
                dry:      false
            }
        ),
        new webpack.ProvidePlugin({
            Promise: 'es6-promise-promise'
        }),
        new CopyWebpackPlugin([
            {
                context: path.resolve(__dirname, '.'),
                from: srcDir + '/img',
                to: 'img'
            },
        ]),
        extractSass,
        new WebpackNotifierPlugin(),
        new ManifestPlugin({

        })
    ];

    if (isProd) {
        plugins = plugins.concat([])
    }

    return {

        entry: {
            'css/app.css': srcDir + '/css/app.scss',
            'css/404.css': srcDir + '/css/404.scss',
            'css/login.css': srcDir + '/css/login.scss',

            'js/app.js': srcDir + '/js/app.js',
            'js/common.js': srcDir + '/js/common.js'
        },
        // Here the application starts executing
        // and webpack starts bundling

        output: {
            // options related to how webpack emits results
            path: path.resolve(__dirname, dist), // string
            // the target directory for all output files
            // must be an absolute path (use the Node.js path module)

            publicPath: "",
            filename: '[name]',
        },

        module: {
            // configuration regarding modules

            rules: [
                { test: /\.hbs/, loader: "handlebars-template-loader" },

                // rules for modules (configure loaders, parser options, etc.)
                {
                    test: /\.woff($|\?)|\.woff2($|\?)|\.ttf($|\?)|\.eot($|\?)|\.svg($|\?)/,
                    use: {
                        loader: 'file-loader',
                        options: {
                            name: 'fonts/[name].[ext]',
                            useRelativePath: false,
                            publicPath: '../'
                        },
                    }
                },
                {
                    test: /\.png($|\?)|\.jpg($|\?)|\.jpeg($|\?)|\.gif($|\?)/,
                    use: {
                        loader: 'file-loader',
                        options: {
                            name: 'img/[name].[ext]',
                            publicPath: '../'
                        },
                    }
                },
                {
                    test: /\.js$/,
                    exclude: /node_modules/,
                    use: [
                        {
                            loader: 'babel-loader',
                            options: {
                                presets: ['env']
                            }
                        },
                        {
                            loader: 'imports-loader?define=>false'
                        }
                    ],
                },
                {
                    test: /\.exec\.js$/,
                    use: {
                        loader: 'script-loader'
                    }
                },
                {
                    test: /\.scss$/,
                    use: extractSass.extract({
                        fallback: 'style-loader',
                        use: [
                            {
                                loader: "css-loader",
                                options: {
                                    sourceMap: true,
                                    //url: false
                                }
                            },
                            {
                                loader: 'postcss-loader',
                                options: {
                                    plugins: [
                                        autoprefixer({
                                            browsers:['ie >= 10', 'last 3 version']
                                        })
                                    ],
                                    sourceMap: true
                                }
                            },
                            {
                                loader: "sass-loader",
                                options: {
                                    sourceMap: true
                                }
                            }
                        ]
                    })
                }
            ]
        },
        node: {
            fs: "empty" // avoids error messages
        },
        resolve: {
            // options for resolving module requests
            // (does not apply to resolving to loaders)

            modules: [
                "node_modules"
                // path.resolve(__dirname, "app")
            ],
            // directories where to look for modules

            alias: {
                'jquery': 'jquery/dist/jquery',
                //handlebars: 'handlebars/dist/handlebars.min.js'
            },

            extensions: ['*', '.js', '.json']

        },
        devtool: isProd ? "source-map" : "inline-cheap-module-source-map",
        cache: true,

        context: __dirname, // string (absolute path!)
        // the home directory for webpack
        // the entry and module.rules.loader option
        //   is resolved relative to this directory

        target: "web", // enum
        // the environment in which the bundle should run
        // changes chunk loading behavior and available modules

        plugins: plugins,
    }
};