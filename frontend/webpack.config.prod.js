const path = require('path');
const webpack = require('webpack');
const CleanWebpackPlugin = require('clean-webpack-plugin');

const PATHS = {
    build: path.resolve(__dirname, '..', 'Logdata-Server-Web', 'src', 'main', 'resources', 'static', 'react')
};

module.exports = {
    entry: {
        app: [
            './src/index'
        ]
    },
    output: {
        path: PATHS.build,
        filename: 'bundle.js',
        publicPath: "/assets/"
    },
    module: {
        rules: [{
            test: /\.js$/,
            exclude: /node_modules/,
            use: {
                loader: 'babel-loader',
                options: {
                    cacheDirectory: true,
                    presets: [
                        'env',
                        ["es2015", {
                            modules: false
                        }],
                        "react"
                    ],
                }
            }
        },
            {
                test: /\.css$/,
                use: [
                    'style-loader',
                    'css-loader'
                ]
            },
            {
                test: /\.(png|svg|jpg|gif)$/,
                use: [
                    'file-loader'
                ]
            },
            {
                test: /\.(woff|woff2|eot|ttf|otf)$/,
                use: [
                    'file-loader'
                ]
            }
        ]
    },
    resolve: {
        modules: ['node_modules'],
        extensions: ['.js', '.json', '.jsx', '.css'],
    },
    plugins: [
        new CleanWebpackPlugin([PATHS.build]),
        new webpack.HotModuleReplacementPlugin(),
        // new webpack.DefinePlugin({
        //     'process.env': {
        //         NODE_ENV: JSON.stringify('production')
        //     }
        // }),
        new webpack.optimize.UglifyJsPlugin()
    ]
};