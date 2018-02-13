const packageJSON = require('./package.json');
const path = require('path');
const webpack = require('webpack');
// const CleanWebpackPlugin = require('clean-webpack-plugin');

const PATHS = {
    build: path.resolve(__dirname, '..', 'backend', 'src', 'main', 'resources', 'static', 'react')
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
        publicPath: "/react/"
    },
    devServer: {
        hot: true,
        historyApiFallback: true,
        inline: true,
        port: 3000,
        publicPath: "/react/",
        contentBase: './src/main/resources/templates',
        proxy: {
            "**": "http://localhost:8080"
        }
    },
    module: {
        rules: [{
            test: /\.js$/,
            exclude: /node_modules/,
            use: {
                loader: require.resolve('babel-loader'),
                options: {
                    cacheDirectory: true,
                    presets: [
                        'env',
                        ["es2015", {
                            modules: false
                        }],
                        "react"
                    ],
                    plugins: ['react-hot-loader/babel']
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
    devtool: 'inline-source-map',
    plugins: [
        // new CleanWebpackPlugin([PATHS.build])
        new webpack.HotModuleReplacementPlugin()
    ]
};